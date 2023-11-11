package ru.example.megamarket.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {
    APPROVE,
    DISAPPROVE,
    AWAITING_CONFIRMATION
}
