package com.bingo.appbingo.infrastructure.driver_adapter.retreats;

import com.bingo.appbingo.domain.model.retreats.Retreats;
import com.bingo.appbingo.domain.model.retreats.gateway.RetreatsRepository;
import com.bingo.appbingo.domain.model.users.gateway.UsersRepository;
import com.bingo.appbingo.domain.model.userwallet.gateway.UserWalletRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.retreats.mapper.RetreatsMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class RetreatsAdapterRepository extends ReactiveAdapterOperations< Retreats,RetreatsEntity,Integer,RetreatsReactiveRepository> implements RetreatsRepository {
    private final UserWalletRepository userWalletRepositoryAdapter;
    private final UsersRepository usersRepository;
    public RetreatsAdapterRepository(RetreatsReactiveRepository repository, UsersRepository usersRepository, JwtProvider jwtProvider, UserWalletRepository userWalletRepositoryAdapter, ObjectMapper mapper) {
        super(repository, mapper, d->mapper.mapBuilder(d,Retreats.RetreatsBuilder.class).build());
        this.userWalletRepositoryAdapter = userWalletRepositoryAdapter;
        this.usersRepository = usersRepository;
    }

    @Override
    public Mono<Response> moneyRequest(Retreats retreats, String token) {
       // userRepository.getUserIdToken(token).
        return null;
    }

    @Override
    public Flux<Retreats> getAllRetreats() {
        return repository.findAll()
                .map(RetreatsMapper::retreatsEntityARetreats);
    }
}
