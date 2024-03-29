package it.tai.springpostresqljpa.springpostresqljpa.exceptions;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

//Classe che gestisce specifiche eccezioni ResourceNotFoundExceptions ed eccezioni globali in un unico posto
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler
{
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @Hidden
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request)
    {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(BadParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @Hidden
    public ErrorMessage badParameterException(BadParameterException ex, WebRequest request)
    {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @Hidden
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        this.log.error(String.format("request: %s, exception: %s", request.getDescription(false), ex.getMessage()), ex);
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @Hidden
    public ErrorMessage handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.FORBIDDEN.value(), new Date(), ex.getMessage(), request.getDescription(false));//"Access denied");
    }
}
