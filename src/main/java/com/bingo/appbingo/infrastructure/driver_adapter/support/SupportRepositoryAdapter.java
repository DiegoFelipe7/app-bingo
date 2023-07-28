package com.bingo.appbingo.infrastructure.driver_adapter.support;

import com.bingo.appbingo.domain.model.enums.StateSupport;
import com.bingo.appbingo.domain.model.support.Support;
import com.bingo.appbingo.domain.model.support.SupportDto;
import com.bingo.appbingo.domain.model.support.gateway.SupportRepository;
import com.bingo.appbingo.domain.model.users.gateway.UsersRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.Utils;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import com.bingo.appbingo.infrastructure.driver_adapter.support.mapper.SupportMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.users.UsersReactiveRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.users.UsersRepositoryAdapter;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public class SupportRepositoryAdapter extends ReactiveAdapterOperations<Support , SupportEntity , Integer , SupportReactiveRepository> implements SupportRepository {
    private final UsersReactiveRepository userRepository;
    private final JwtProvider jwtProvider;
    public SupportRepositoryAdapter(SupportReactiveRepository repository, ObjectMapper mapper , UsersReactiveRepository userRepository , JwtProvider jwtProvider) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Support.SupportBuilder.class).build());
        this.userRepository=userRepository;
        this.jwtProvider=jwtProvider;
    }

    @Override
    public Flux<SupportDto> getAllSupport() {
        return repository.findAll()
                .flatMap(ele -> userRepository.findById(ele.getUserId())
                        .map(data -> new SupportDto(ele.getId(),
                                ele.getTicket(),
                                ele.getCategory(),
                                ele.getQuestion(),
                                ele.getAnswer(),
                                data.getUsername(),
                                data.getEmail(),
                                ele.getUrlPhoto(),
                                ele.getState(),
                                ele.getCreatedAt(),
                                ele.getUpdatedAt())));
    }

    @Override
    public Flux<Support> getAllSupportUser(String token) {
        String username = jwtProvider.extractToken(token);
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Error filtrando el usuario! ", TypeStateResponse.Error)))
                .flatMapMany(ele -> repository.findAll()
                        .filter(data -> data.getUserId().equals(ele.getId()))
                        .map(SupportMapper::supportEntityASupport));
    }

    @Override
    public Mono<Support> getAllSupportId(Integer id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "No existe un ticket de soporte con esta informacion!", TypeStateResponse.Warning)))
                .map(SupportMapper::supportEntityASupport);

    }

    @Override
    public Mono<Response> saveSupport(Support support , String token) {
        String username = jwtProvider.extractToken(token);
        return userRepository.findByUsername(username)
                .flatMap(ele -> {
                    support.setTicket(Utils.uid());
                    support.setUserId(ele.getId());
                    support.setState(StateSupport.Pending);
                    return repository.save(SupportMapper.supportASupportEntity(support))
                            .thenReturn(new Response(TypeStateResponses.Success, "Solicitud enviada!"));
                });

    }

    @Override
    public Mono<Response> editSupport(Support support , Integer id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "No existe un ticket de soporte con esta informacion!", TypeStateResponse.Warning)))
                .flatMap(ele -> {
                    ele.setUpdatedAt(LocalDateTime.now());
                    ele.setAnswer(support.getAnswer());
                    ele.setState(StateSupport.Reply);
                    return repository.save(ele)
                            .thenReturn(new Response(TypeStateResponses.Success, "Mensaje enviado"));
                });
    }
}
