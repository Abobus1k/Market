package ru.example.megamarket.deposit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositResponse {
    private Integer id;
    private Integer userId;
    private Integer sum;
}
