package tech.ada.mercado.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tech.ada.mercado.util.ApiError;
import tech.ada.mercado.util.ApiFieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * Criando uma classe responsável por capturar e tratar as exception
 * que são lançadas na aplicação.
 */
@ControllerAdvice
public class HandlerExceptionAdvice extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ResponseException> handleSecurity(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(InternalServerError.class)
    protected ResponseEntity<ResponseException> handleSecurity(InternalServerError e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseException> handleSecurity(BusinessException e){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ResponseException(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<Object>>  handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange request) {
        List<ApiError> erros = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String sigla = fieldError.getField().replaceAll("[^.a-zA-Z]", "");
            erros.add(new ApiFieldError(HttpStatus.BAD_REQUEST.value(), mensagem, sigla));
        });

        return handleExceptionInternal(ex, erros.stream().findFirst(), headers, HttpStatus.BAD_REQUEST, request);
    }

}