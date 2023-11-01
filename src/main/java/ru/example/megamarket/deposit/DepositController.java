package ru.example.megamarket.deposit;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/deposits")
@RequiredArgsConstructor
public class DepositController {
    private final DepositService service;

    @PostMapping
    public ResponseEntity<Void> createDeposit(@RequestBody DepositRequest request, Principal connectedUser) {
        service.addDeposit(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
