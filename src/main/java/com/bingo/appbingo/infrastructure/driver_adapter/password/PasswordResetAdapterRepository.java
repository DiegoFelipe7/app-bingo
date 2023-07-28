package com.bingo.appbingo.infrastructure.driver_adapter.password;

import com.bingo.appbingo.domain.model.auth.Login;
import com.bingo.appbingo.domain.model.passwordreset.PasswordReset;
import com.bingo.appbingo.domain.model.passwordreset.gateway.PasswordResetRepository;
import com.bingo.appbingo.domain.model.utils.Response;
import com.bingo.appbingo.domain.model.utils.TypeStateResponses;
import com.bingo.appbingo.infrastructure.driver_adapter.auth.AuthReactiveRepository;
import com.bingo.appbingo.infrastructure.driver_adapter.auth.EmailService;
import com.bingo.appbingo.infrastructure.driver_adapter.auth.UsersEntity;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Repository
public class PasswordResetAdapterRepository extends ReactiveAdapterOperations<PasswordReset, PasswordResetEntity, Integer, PasswordReactiveResetRepository> implements PasswordResetRepository {
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthReactiveRepository authReactiveRepository;

    public PasswordResetAdapterRepository(PasswordReactiveResetRepository repository, AuthReactiveRepository authReactiveRepository, EmailService emailService, ObjectMapper mapper, PasswordEncoder passwordEncoder) {
        super(repository, mapper, d -> mapper.mapBuilder(d, PasswordReset.PasswordResetBuilder.class).build());
        this.authReactiveRepository = authReactiveRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Mono<Response> passwordRecovery(Login login) {
        return authReactiveRepository.findByEmailIgnoreCase(login.getEmail())
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "El usuario no se encuentra registrado!", TypeStateResponse.Error)))
                .filter(UsersEntity::getStatus)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "No tienes tu cuenta verificada", TypeStateResponse.Warning)))
                .flatMap(ele -> {
                    PasswordResetEntity passwordResetEntity = new PasswordResetEntity(ele.getEmail(), UUID.randomUUID().toString(), LocalDateTime.now().plusHours(1));
                    return repository.save(passwordResetEntity).flatMap(data -> emailService.sendEmailRecoverPassword(ele.getFullName(), data.getEmail(), data.getToken())
                            .then(Mono.just(new Response(TypeStateResponses.Success, "se envió un correo electrónico con la opción de actualizar su contraseña,tienes una hora para realizar la actualizacion."))));
                });

    }



    @Override
    public Mono<Response> passwordChange(String token, Login login) {
        ZoneId colombiaZone = ZoneId.of("America/Bogota");
        ZonedDateTime currentTime = ZonedDateTime.now(colombiaZone);
        return repository.findByToken(token)
                .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, "Lo sentimos, el token no es valido!", TypeStateResponse.Error)))
                .flatMap(data -> {
                    if (data.getDuration().isAfter(currentTime.toLocalDateTime())) {
                        return authReactiveRepository.findByEmailIgnoreCase(data.getEmail())
                                .flatMap(ele -> {
                                    ele.setId(ele.getId());
                                    String encodedPassword = passwordEncoder.encode(login.getPassword());
                                    ele.setPassword(encodedPassword);
                                    ele.setToken(null);
                                    return authReactiveRepository.save(ele)
                                            .thenReturn(new Response(TypeStateResponses.Success, "Contraseña actualizada!"));
                                });
                    }
                    return Mono.error(new CustomException(HttpStatus.BAD_REQUEST, "EL token expiro realiza una nueva solicitud de cambio de contraseña", TypeStateResponse.Warning));
                });

    }
}
