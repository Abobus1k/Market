package ru.example.megamarket.deposit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PagedDepositResponse checkAllDeposits(@RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        Page<Deposit> page = service.adminGetAllDeposits(PageRequest.of(offset, limit));
        return PagedDepositResponse.builder()
                .totalPages(page.getTotalPages())
                .depositResponseList(page.getContent().stream().map(mapper::depositToDepositResponse).toList())
                .build();
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{depositId}")
    @Operation(operationId = "Удаление заявки на депозит средств и установление статуса")
    public ResponseEntity<Void> manageDeposit(@PathVariable Integer depositId, @RequestParam Boolean approved) {
        service.adminDeleteDeposit(depositId, approved);
        return ResponseEntity.noContent().build();
    }
}