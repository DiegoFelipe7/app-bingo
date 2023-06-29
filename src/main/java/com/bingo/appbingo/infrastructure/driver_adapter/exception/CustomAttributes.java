package com.bingo.appbingo.infrastructure.driver_adapter.exception;


import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new HashMap<>();
        Throwable throwable = super.getError(request);
        if(throwable instanceof CustomException) {

            CustomException customException = (CustomException) throwable;
            errorAttributes.put("status", customException.getStatus());
            errorAttributes.put("message", customException.getMessage());
            errorAttributes.put("typeStatus" , customException.getTypeStatus());
        }
        if (throwable instanceof DataIntegrityViolationException) {
            String message = "Alguno de los campos ya existe en la base de datos!";
            errorAttributes.put("status", HttpStatus.BAD_REQUEST);
            errorAttributes.put("message", message);
            errorAttributes.put("typeStatus" , "Warning");
        }
        if(throwable instanceof TransientDataAccessResourceException){
            String message = "Ocurrio un error en la actualizacion de alguno datos comunicate con el administrador!";
            errorAttributes.put("status", HttpStatus.BAD_REQUEST);
            errorAttributes.put("message", message);
            errorAttributes.put("typeStatus" , "Warning");

        }
        if (throwable instanceof IllegalArgumentException) {
            String message = "Ocurrio un error en un argumento invalido comunica el administrador!";
            errorAttributes.put("status", HttpStatus.BAD_REQUEST);
            errorAttributes.put("message", message);
            errorAttributes.put("typeStatus" , "Warning");
        }

        return errorAttributes;
    }
}
