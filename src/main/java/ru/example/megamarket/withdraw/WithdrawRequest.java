package ru.example.megamarket.withdraw;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRequest {
    @NotNull(message = "Сумма для вывода не заполнена")
    @Min(value = 1, message = "Нельзя выводить менее одного рубля")
    private Integer sum;
}
