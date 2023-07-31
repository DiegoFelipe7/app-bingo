package com.bingo.appbingo.infrastructure.driver_adapter.auth;

import com.bingo.appbingo.infrastructure.driver_adapter.exception.CustomException;
import com.bingo.appbingo.infrastructure.driver_adapter.exception.TypeStateResponse;
import com.bingo.appbingo.infrastructure.driver_adapter.helper.MessageHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Value("${bet_billions.email.sender}")
    private String emailSender;
    private final JavaMailSender mailSender;
    private final Scheduler scheduler;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.scheduler = Schedulers.boundedElastic();
    }

    public Mono<Void> sendEmailWelcome(String fullName, String email, String token) {
        MimeMessage message = mailSender.createMimeMessage();
        String welcome = MessageHtml.welcome(fullName,token);
        return Mono.fromRunnable(() -> {
            try {
                message.setSubject("Bienvenido a bet billions");
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(emailSender);
                helper.setTo(email);
                helper.setText(welcome, true);
                mailSender.send(message);
            } catch (Exception e) {
                System.out.println(e);
                Mono.error(new CustomException(HttpStatus.BAD_REQUEST, e.getMessage(), TypeStateResponse.Error));
            }
        }).subscribeOn(scheduler).then();
    }

    public Mono<Void> sendEmailRecoverPassword(String fullName, String email, String token) {

        MimeMessage message = mailSender.createMimeMessage();
        String recovery = MessageHtml.recoverPassword(fullName,token);

        return Mono.fromRunnable(() -> {
            try {
                message.setSubject("Recuperar contraseña bet billions");
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(emailSender);
                helper.setTo(email);
                helper.setText(recovery, true);
                mailSender.send(message);
            } catch (Exception e) {
                Mono.error(new CustomException(HttpStatus.BAD_REQUEST, e.getMessage(), TypeStateResponse.Error));
            }
        }).subscribeOn(scheduler).then();
    }

    public Mono<Void> invalidTransaction(String fullName, String email) {
        MimeMessage message = mailSender.createMimeMessage();
        String invalid = MessageHtml.invalidTransaction(fullName);
        return Mono.fromRunnable(() -> {
            try {
                message.setSubject("Error en hash de transacción");
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(emailSender);
                helper.setTo(email);
                helper.setText(invalid, true);
                mailSender.send(message);
            } catch (Exception e) {
                Mono.error(new CustomException(HttpStatus.BAD_REQUEST, e.getMessage(), TypeStateResponse.Error));
            }
        }).subscribeOn(scheduler).then();
    }

    public Mono<Void>sendNotification(String fullName, String email) {
        MimeMessage message = mailSender.createMimeMessage();
        String invalid = MessageHtml.retreats(fullName);
        return Mono.fromRunnable(() -> {
            try {
                message.setSubject("Transacción exitosa");
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(emailSender);
                helper.setTo(email);
                helper.setText(invalid, true);
                mailSender.send(message);
            } catch (Exception e) {
                Mono.error(new CustomException(HttpStatus.BAD_REQUEST, e.getMessage(), TypeStateResponse.Error));
            }
        }).subscribeOn(scheduler).then();
    }
}
