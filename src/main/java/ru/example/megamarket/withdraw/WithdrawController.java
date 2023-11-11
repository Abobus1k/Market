package ru.example.megamarket.withdraw;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/withdraws")
@RequiredArgsConstructor
@Tag(name = "withdraw", description = "Работа с выводом средств")
public class WithdrawController {
    private final WithdrawService service;
    private final WithdrawMapper mapper;

    @PostMapping
    @Operation(description = "Создание заявки на вывод средств")
    public WithdrawResponse createWithdraw(@RequestBody @Valid WithdrawRequest request, Principal connectedUser) {
        return mapper.withdrawToWithdrawResponse(service.addWithdraw(request, connectedUser));
    }
}
