package com.bingo.appbingo.infrastructure.driver_adapter.round;

import com.bingo.appbingo.domain.model.round.Round;
import com.bingo.appbingo.domain.model.round.gateway.RoundRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import java.util.List;

@Repository
public class RoundAdapterRepository extends ReactiveAdapterOperations<Round , RoundEntity , Integer , RoundReactiveRepository> implements RoundRepository {
    public RoundAdapterRepository(RoundReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d->mapper.mapBuilder(d,Round.RoundBuilder.class).build());
    }

    @Override
    public Mono<Void> saveRounds(List<Round> round) {
        return null;
    }
}
