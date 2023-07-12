package com.bingo.appbingo.infrastructure.driver_adapter.users;

import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.enums.TypeHistory;
import com.bingo.appbingo.domain.model.history.gateway.PaymentHistoryRepository;
import com.bingo.appbingo.domain.model.users.PanelUsers;
import com.bingo.appbingo.domain.model.users.References;
import com.bingo.appbingo.domain.model.users.gateway.UserRepository;
import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.auth.UsersEntity;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.Utils;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import com.bingo.appbingo.infrastructure.driver_adapter.users.mapper.UserMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.userwallet.UserWalletEntity;
import com.bingo.appbingo.infrastructure.driver_adapter.userwallet.UserWalletReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UsersRepositoryAdapter extends ReactiveAdapterOperations<Users, UsersEntity, Integer, UsersReactiveRepository> implements UserRepository {
    private final JwtProvider jwtProvider;
    private final UserWalletRepository userWalletRepository;
    private final UserWalletReactiveRepository userWalletReactiveRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private static final Integer LEVEL = 4;

    public UsersRepositoryAdapter(UsersReactiveRepository repository, UserWalletRepository userWalletRepository, UserWalletReactiveRepository userWalletReactiveRepository, PaymentHistoryRepository paymentHistoryRepository, ObjectMapper mapper, JwtProvider jwtProvider) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Users.UsersBuilder.class).build());
        this.jwtProvider = jwtProvider;
        this.userWalletRepository = userWalletRepository;
        this.userWalletReactiveRepository = userWalletReactiveRepository;
        this.paymentHistoryRepository = paymentHistoryRepository;
    }


    @Override
    public Flux<References> getAllReferences(String token) {
        String userName = jwtProvider.extractToken(token);
        AtomicInteger counter = new AtomicInteger(0);
        return repository.findByUsername(userName)
                .flatMapMany(user -> repository.findAll()
                        .filter(ele -> Objects.equals(ele.getParentId(), user.getId()))
                        .filter(ele -> !ele.getUsername().equals(userName))
                        .map(ele -> {
                            Integer currentCount = counter.incrementAndGet();
                            return UserMapper.referencesDirect(ele, currentCount);
                        }).sort(Comparator.comparing(References::getId)));


    }

    @Override
    public Flux<References> getAllReferencesTeam(String token) {
        String userName = jwtProvider.extractToken(token);
        AtomicInteger counter = new AtomicInteger(0);
        return repository.findUserAndDescendantsTeam(userName)
                .map(ele -> {
                    Integer currentCount = counter.incrementAndGet();
                    return UserMapper.referencesLevel(ele, currentCount);
                }).sort(Comparator.comparing(References::getId));

    }

    @Override
    public Flux<Users> getAllUsers() {
        return repository.findAll().map(UserMapper::usersEntityAUsers);
    }

    @Override
    public Mono<Response> editUser(Users user) {
        return repository.findByEmail(user.getEmail())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Se ha producido un error, inténtelo de nuevo.", TypeStateResponse.Warning)))
                .flatMap(ele -> {
                    ele.setId(ele.getId());
                    ele.setPhoto(user.getPhoto());
                    ele.setPhone(user.getPhone());
                    ele.setCountry(user.getCountry());
                    ele.setCity(user.getCity());
                    return repository.save(ele)
                            .thenReturn(new Response(TypeStateResponses.Success, "Datos actualizados"));
                });
    }


    @Override
    public Mono<Users> getUserId(String token) {
        String username = jwtProvider.extractToken(token);
        return repository.findByUsername(username)
                .map(UserMapper::usersEntityAUsers);
    }

    @Override
    public Mono<PanelUsers> panelUser(String token) {
        String username = jwtProvider.extractToken(token);
        Mono<UsersEntity> usersEntity = repository.findByUsername(username);
        Mono<Long> referenceCount = repository.findUserAndDescendantsTeam(username).count();
        Mono<UserWalletEntity> userWalletEntity = usersEntity.flatMap(user -> userWalletReactiveRepository.findByUserId(user.getId()));
        Mono<BigDecimal> balance = paymentHistoryRepository.filterPaymentHistory(TypeHistory.Earnings, token)
                .reduce(BigDecimal.ZERO, (acc, res) -> acc.add(res.getBalance()));
        return Mono.zip(usersEntity, referenceCount, userWalletEntity, balance)
                .flatMap(el -> Mono.just(new PanelUsers(el.getT1().getRefLink(), el.getT3().getBalance(), el.getT4(), el.getT2().intValue())));

    }

    @Override
    public Mono<Void> activateUserNetwork(Integer userId) {
        return repository.findById(userId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Ocurrio un error, intentalo nuevamente", TypeStateResponse.Error)))
                .flatMap(ele -> {
                    ele.setId(ele.getId());
                    ele.setValidForCommissions(Boolean.TRUE);
                    return repository.save(ele);
                }).then();
    }

    @Override
    public Mono<Void> distributeCommission(Integer userId, BigDecimal total) {
        return repository.findById(userId)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Error en la reparticion de comisiones", TypeStateResponse.Error)))
                .flatMapMany(user -> repository.findUserAndParents(user.getUsername(), LEVEL)
                        .flatMap(ele -> {
                            BigDecimal payment = total.multiply(BigDecimal.valueOf(Utils.bonus(ele.getLevel()))).setScale(1, RoundingMode.UNNECESSARY);
                            return userWalletRepository.increaseBalance(ele.getId(), payment, TypeHistory.Commission);
                        })).then();
    }


}
