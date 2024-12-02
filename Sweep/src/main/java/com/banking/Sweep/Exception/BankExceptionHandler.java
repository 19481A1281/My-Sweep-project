package com.banking.Sweep.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BankExceptionHandler {
    @ExceptionHandler(value = {InsufficientFundsException.class})
    public ResponseEntity<Object> handleInsufficientFundsException(InsufficientFundsException insufficientFundsException){
        BankException bankException=new BankException(
                insufficientFundsException.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(bankException,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DuplicateEmailException.class})
    public ResponseEntity<Object> handleDuplicateEmailException(DuplicateEmailException duplicateEmailException){
        BankException bankException=new BankException(
                duplicateEmailException.getMessage(),
                duplicateEmailException.getCause(),
                HttpStatus.CONFLICT
        );
        return new ResponseEntity<>(bankException,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {DoesNotExistException.class})
    public ResponseEntity<Object> handleDoesNotExistException(DoesNotExistException doesNotExistException){
        BankException bankException=new BankException(
                doesNotExistException.getMessage(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(bankException,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {SweepSetupException.class})
    public ResponseEntity<Object> handleSweepSetupException(SweepSetupException sweepSetupException){
        BankException bankException=new BankException(
                sweepSetupException.getMessage(),
                //sweepSetupException.getCause(),
                HttpStatus.NOT_ACCEPTABLE
        );
        return new ResponseEntity<>(bankException,HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = {PasswordPatternMissMatchException.class})
    public ResponseEntity<Object> handlePasswordPatternMissMatchException(PasswordPatternMissMatchException passwordPatternMissMatchException){
        BankException bankException=new BankException(
                passwordPatternMissMatchException.getMessage(),
                passwordPatternMissMatchException.getCause(),
                HttpStatus.NOT_ACCEPTABLE
        );
        return new ResponseEntity<>(bankException,HttpStatus.NOT_ACCEPTABLE);
    }

}
