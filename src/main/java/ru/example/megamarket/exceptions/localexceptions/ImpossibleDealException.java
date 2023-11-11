package ru.example.megamarket.exceptions.localexceptions;

public class ImpossibleDealException extends RuntimeException {
    public ImpossibleDealException(String message) {
        super(message);
    }
}
