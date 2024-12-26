package br.com.hadryan.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StockNotAvailableException extends ResponseStatusException {

    public StockNotAvailableException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
