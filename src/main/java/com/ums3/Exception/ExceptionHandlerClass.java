package com.ums3.Exception;

import com.ums3.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(
            Exception exception,
            WebRequest webRequest
    ){
        ErrorDetails error = new ErrorDetails(exception.getMessage(), new Date(),webRequest.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
