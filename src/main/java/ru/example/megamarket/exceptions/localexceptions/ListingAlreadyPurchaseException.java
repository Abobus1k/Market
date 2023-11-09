package ru.example.megamarket.exceptions.localexceptions;

public class ListingAlreadyPurchaseException extends RuntimeException {
    public ListingAlreadyPurchaseException(String message) {
        super(message);
    }
}
