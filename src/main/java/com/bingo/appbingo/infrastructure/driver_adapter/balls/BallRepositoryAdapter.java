package com.bingo.appbingo.infrastructure.driver_adapter.balls;

import com.bingo.appbingo.domain.model.balls.Balls;
import com.bingo.appbingo.domain.model.balls.gateway.BallRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public class BallRepositoryAdapter extends ReactiveAdapterOperations<Balls, BallsEntity , Integer , BallReactiveRepository>  implements BallRepository {
    public BallRepositoryAdapter(BallReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d->mapper.mapBuilder(d,Balls.BallsBuilder.class).build());
    }

    @Override
    public Mono<Balls> getAllBall() {
        return null;
    }
}
