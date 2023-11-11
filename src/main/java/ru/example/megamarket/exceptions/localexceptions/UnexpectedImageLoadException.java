package ru.example.megamarket.exceptions.localexceptions;

public class UnexpectedImageLoadException extends RuntimeException {
    public UnexpectedImageLoadException(String message) {
        super(message);
    }
}
