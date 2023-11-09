package ru.example.megamarket.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.example.megamarket.exceptions.localexceptions.DuplicateException;
import ru.example.megamarket.exceptions.localexceptions.ExtraReviewException;
import ru.example.megamarket.exceptions.localexceptions.InsufficientFundsException;
import ru.example.megamarket.exceptions.localexceptions.ListingAlreadyPurchaseException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleErrorService {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        Map<String, Object> response = new HashMap<>();
        response.put("errors", errorMap);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("resultCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Object> handleDuplicateException(DuplicateException ex){
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("resultCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Object> handleInsufficientFundsException(InsufficientFundsException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("resultCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ListingAlreadyPurchaseException.class)
    public ResponseEntity<Object> handleListingAlreadySoldException(ListingAlreadyPurchaseException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("resultCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExtraReviewException.class)
    public ResponseEntity<Object> handleExtraReviewException(ExtraReviewException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("resultCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
