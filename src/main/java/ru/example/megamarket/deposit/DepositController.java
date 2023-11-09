package ru.example.megamarket.deposit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/deposits")
@RequiredArgsConstructor
@Tag(name = "deposit", description = "Работа с депозитами")
public class DepositController {
    private final DepositService service;

    @PostMapping
    @Operation(description = "Создание заявки на депозит")
    public ResponseEntity<Void> createDeposit(@RequestBody @Valid DepositRequest request, Principal connectedUser) {
        service.addDeposit(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
