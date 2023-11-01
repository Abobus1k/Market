package ru.example.megamarket.withdraw;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/withdraws")
@RequiredArgsConstructor
public class WithdrawController {
    private final WithdrawService service;

    @PostMapping
    public ResponseEntity<Void> createWithdraw(@RequestBody WithdrawRequest request, Principal connectedUser) {
        service.addWithdraw(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
