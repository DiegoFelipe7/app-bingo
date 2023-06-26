package com.bingo.appbingo.infrastructure.driver_adapter.auth;


import com.bingo.appbingo.domain.model.auth.Login;
import com.bingo.appbingo.domain.model.auth.Role;
import com.bingo.appbingo.domain.model.auth.Token;
import com.bingo.appbingo.domain.model.auth.Users;
import com.bingo.appbingo.domain.model.auth.gateways.UserRepository;
import com.bingo.appbingo.domain.model.session.Session;
import com.bingo.appbingo.domain.model.session.gateway.SessionsRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.auth.mapper.UserMapper;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.Utils;
import com.bingo.appbingo.infrastructure.driver_adapter.security.jwt.JwtProvider;
import com.bingo.appbingo.infrastructure.driver_adapter.session.SessionEntity;
import com.bingo.appbingo.infrastructure.driver_adapter.session.SessionRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.session.mapper.SessionMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class AuthRepositoryAdapter extends ReactiveAdapterOperations<Users, UsersEntity, Integer, AuthReactiveRepository> implements UserRepository, SessionsRepository {
    private final SessionRepository sessionRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    @Value("${evox.url}")
    private String url;

    private final JwtProvider jwtProvider;

    public AuthRepositoryAdapter(AuthReactiveRepository repository, SessionRepository sessionsRepository, EmailService emailService, ObjectMapper mapper, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Users.UsersBuilder.class).build());
        this.sessionRepository = sessionsRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.emailService=emailService;
    }


    @Override
    public Mono<Token> login(Login login) {
        return repository.findByEmailIgnoreCase(login.getEmail())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "El usuario no esta registrado!", TypeStateResponse.Error)))
                .filter(user -> passwordEncoder.matches(login.getPassword(), user.getPassword()))
                .flatMap(user -> {
                    if (Boolean.TRUE.equals(user.getStatus())) {
                        return entryRegister(login).map(ele -> new Token(jwtProvider.generateToken(user)));
                    }
                    return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "El usuario tiene la cuenta inactiva , verifica tu correo electronico y activa tu cuenta", TypeStateResponse.Warning));
                })
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Contraseña invalida!", TypeStateResponse.Error)));
    }
    @Override
    public Mono<Session> entryRegister(Login login) {
        return repository.findByEmailIgnoreCase(login.getEmail())
                .flatMap(ele -> {
                    SessionEntity session = new SessionEntity(ele.getId(), login.getIpAddress(), login.getCountry(), login.getBrowser(), LocalDateTime.now());
                    return sessionRepository.save(session).map(SessionMapper::sessionEntityASession);
                });
    }

    @Override
    public Mono<Response> accountRegistration(Users user) {
        user.setRefLink(url + user.getUsername());
        user.setRoles(Role.ROLE_USER.name());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setToken(UUID.randomUUID().toString());
        if (user.getInvitationLink() == null) {
            return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "Necesitas un link de referido para tu registro!", TypeStateResponse.Warning));
        }
        return referral(user);
    }

    @Override
    public Mono<Response> referral(Users user) {
       return repository.findByUsername(Utils.extractUsername(user.getInvitationLink()))
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "El link de referido no existe!", TypeStateResponse.Warning)))
                .flatMap(parent -> {
                    user.setParentId(parent.getId());
                    return repository.save(UserMapper.usersAUserEntity(user)).flatMap(ele ->
                            emailService.sendEmailWelcome(ele.getFullName(), ele.getEmail(), ele.getToken())
                                    .then(Mono.just(new Response(TypeStateResponses.Success, "Hemos enviado un correo electrónico para la activacion de tu cuenta!" + ele.getFullName()))));
                });
    }

    @Override
    public Mono<Boolean> validateToken(String token) {
        return Mono.just(jwtProvider.validate(token));
    }



    @Override
    public Mono<Response> activateAccount(String token) {
          return repository.findByToken(token)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "El token es invalido!", TypeStateResponse.Error)))
                .filter(user -> !user.getStatus())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "La cuenta ya esta activa!", TypeStateResponse.Warning)))
                .flatMap(user -> {
                    user.setStatus(true);
                    user.setToken(null);
                    user.setEmailVerified(LocalDateTime.now());
                    return repository.save(user)
                            .map(data -> new Response(TypeStateResponses.Success, "Verificación exitosa"));
                });
    }


}
