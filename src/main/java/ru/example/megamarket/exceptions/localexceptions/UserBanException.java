package ru.example.megamarket.exceptions.localexceptions;

public class UserBanException extends RuntimeException {
    public UserBanException(String message) {
        super(message);
    }
}
