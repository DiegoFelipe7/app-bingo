package com.bingo.appbingo.infrastructure.driver_adapter.users;

import com.bingo.appbingo.infrastructure.driver_adapter.auth.UsersEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UsersReactiveRepository extends ReactiveCrudRepository<UsersEntity, Integer>, ReactiveQueryByExampleExecutor<UsersEntity> {

    Mono<UsersEntity> findByUsername(String username);

    Mono<UsersEntity> findByEmail(String email);

    @Query(value = """
    WITH RECURSIVE user_team AS (
      SELECT u.id, u.full_name, u.phone, u.username, u.created_at, 0 AS level
      FROM users u
      WHERE u.username = :username
      UNION ALL
      SELECT u.id, u.full_name, u.phone, u.username, u.created_at, ut.level + 1 AS level
      FROM users u 
      INNER JOIN user_team ut ON u.parent_id = ut.id
      WHERE ut.level < 4
    )
    SELECT * FROM user_team WHERE username <> :username""")
    Flux<UsersEntity> findUserAndDescendantsTeam(@Param("username") String username);

    @Query(value = """
    WITH RECURSIVE user_team AS (
      SELECT u.id, u.full_name, u.phone, u.username, u.created_at, u.parent_id, 0 AS level ,  u.valid_for_commissions
      FROM users u
      WHERE u.username = :username
      UNION ALL
      SELECT u.id, u.full_name, u.phone, u.username, u.created_at, u.parent_id, ut.level + 1 AS level , u.valid_for_commissions
      FROM users u
      INNER JOIN user_team ut ON u.id = ut.parent_id
    )
    SELECT * FROM user_team
    WHERE level <= :level and username <> :username and valid_for_commissions=true ;
    """)
    Flux<UsersEntity> findUserAndParents(@Param("username") String username , @Param("level") Integer level);

}
