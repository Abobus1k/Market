package ru.example.megamarket.deposit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/deposits")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "admin_deposit", description = "Работа с депозитами для админа")
public class AdminDepositController {
    private final DepositService service;
    private final DepositMapper mapper;

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping
    @Operation(operationId = "Просмотр заявок на депозит")
    public List<DepositResponse> checkAllDeposits() {
        return service.adminGetAllDeposits()
                .stream()
                .map(mapper::depositToDepositResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{depositId}")
    @Operation(operationId = "Удаление заявки на депозит средств и установление статуса")
    public ResponseEntity<Void> manageDeposit(@PathVariable Integer depositId, @RequestParam Boolean approved) {
        service.adminDeleteDeposit(depositId, approved);
        return ResponseEntity.noContent().build();
    }
}