package ru.example.megamarket.withdraw;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawResponse {
    private Integer id;
    private Integer userId;
    private Integer sum;
}
