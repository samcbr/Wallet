package com.wallet.exception;

import com.wallet.payload.WalletResponsePayload;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class WalletExceptionHandler {

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    public ResponseEntity<Object> userNameAlreadyExistsException(UserNameAlreadyExistsException ex) {
        return new ResponseEntity<>(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.FAILURE, null, "User name already exists"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<WalletResponsePayload> badCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.FAILURE, null, "Invalid user name or password"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WalletResponsePayload> methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        return new ResponseEntity<>(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.FAILURE, null, errors.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIdDoesNotExistException.class)
    public ResponseEntity<WalletResponsePayload> userIdDoesNotExistException(UserIdDoesNotExistException ex) {
        return new ResponseEntity<>(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.FAILURE, null, "User Id does not exist"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPaymentException.class)
    public ResponseEntity<WalletResponsePayload> invalidPaymentException(InvalidPaymentException ex) {
        return new ResponseEntity<>(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.FAILURE, null, "Sender and Receiver cannot be the same user"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<WalletResponsePayload> insufficientBalanceException(InsufficientBalanceException ex) {
        return new ResponseEntity<>(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.FAILURE, null, "Your balance is insufficient"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordDoesNotMatchException.class)
    public ResponseEntity<WalletResponsePayload> passwordDoesNotMatchException(PasswordDoesNotMatchException ex) {
        return new ResponseEntity<>(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.FAILURE, null, "Password does not match confirm password"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<WalletResponsePayload> invalidPasswordException(InvalidPasswordException ex) {
        return new ResponseEntity<>(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.FAILURE, null, "Password does not match"), HttpStatus.BAD_REQUEST);
    }

}

