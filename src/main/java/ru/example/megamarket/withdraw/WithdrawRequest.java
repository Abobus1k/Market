package ru.example.megamarket.withdraw;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRequest {
    @NotEmpty(message = "Сумма для вывода не заполнена")
    @Min(value = 1, message = "Нельзя выводить менее одного рубля")
    private Integer sum;
}
