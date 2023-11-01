package ru.example.megamarket.deposit;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/deposits")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminDepositController {
    private final DepositService service;
    private final DepositMapper mapper;

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping
    public List<DepositResponse> checkAllDeposits() {
        return service.adminGetAllDeposits()
                .stream()
                .map(mapper::depositToDepositResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{depositId}")
    public ResponseEntity<Void> manageDeposit(@PathVariable Integer depositId, @RequestParam Boolean approved) {
        service.adminDeleteDeposit(depositId, approved);
        return ResponseEntity.ok().build();
    }
}