package ru.example.megamarket.exceptions.localexceptions;

public class ImpossibleSearchException extends RuntimeException {
    public ImpossibleSearchException(String message) {
        super(message);
    }
}
