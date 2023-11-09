package ru.example.megamarket.deposit;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequest {
    @NotNull(message = "Сумма для депозита не заполнена")
    @Min(value = 1, message = "Нельзя сделать депозит менее одного рубля")
    private Integer sum;
}
