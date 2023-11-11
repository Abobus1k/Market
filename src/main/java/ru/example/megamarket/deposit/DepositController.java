package ru.example.megamarket.deposit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/deposits")
@RequiredArgsConstructor
@Tag(name = "deposit", description = "Работа с депозитами")
public class DepositController {
    private final DepositService service;
    private final DepositMapper mapper;

    @PostMapping
    @Operation(description = "Создание заявки на депозит")
    public DepositResponse createDeposit(@RequestBody @Valid DepositRequest request, Principal connectedUser) {
        return mapper.depositToDepositResponse(service.addDeposit(request, connectedUser));
    }
}
