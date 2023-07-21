package com.bingo.appbingo.infrastructure.driver_adapter.balls;

import com.bingo.appbingo.domain.model.balls.Balls;
import com.bingo.appbingo.domain.model.balls.gateway.BallRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.balls.mapper.BallsMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@Repository
public class BallRepositoryAdapter extends ReactiveAdapterOperations<Balls, BallsEntity, Integer, BallReactiveRepository> implements BallRepository {
    public BallRepositoryAdapter(BallReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Balls.BallsBuilder.class).build());
    }


    @Override
    public Flux<Balls> getAllBall() {
        return repository.findAll()
                .map(BallsMapper::ballsEntityABalls)
                .collectList()
                .flatMapMany(list -> {
                    List<Balls> randomBalls = getRandomElements(list, 75);
                    return Flux.fromIterable(randomBalls);
                })
                .delayElements(Duration.ofSeconds(5))
                .subscribeOn(Schedulers.parallel());
    }

    private List<Balls> getRandomElements(List<Balls> list, int count) {
        int totalElements = Math.min(count, list.size());
        List<Balls> randomElements = new ArrayList<>(totalElements);
        Random random = new Random();
        while (randomElements.size() < totalElements) {
            Balls randomBall = list.get(random.nextInt(list.size()));
            if (!randomElements.contains(randomBall)) {
                randomElements.add(randomBall);
            }
        }
        return randomElements;
    }

}
