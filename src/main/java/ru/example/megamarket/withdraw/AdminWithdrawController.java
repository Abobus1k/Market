package ru.example.megamarket.withdraw;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/withdraws")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "admin_withdraw", description = "Работа с выводами средств для админа")
public class AdminWithdrawController {
    private final WithdrawService service;
    private final WithdrawMapper mapper;

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping
    @Operation(operationId = "Просмотр заявок на вывод средств")
    public List<WithdrawResponse> checkAllWithdraws(@RequestParam(defaultValue = "0") Integer offset, @RequestParam(defaultValue = "10") Integer limit) {
        return service.adminGetAllWithdraws(PageRequest.of(offset, limit))
                .stream()
                .map(mapper::withdrawToWithdrawResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{withdrawId}")
    @Operation(operationId = "Удаление заявки на вывод средств и установление статуса")
    public ResponseEntity<Void> manageWithdraw(@PathVariable Integer withdrawId, @RequestParam Boolean approved) {
        service.adminDeleteWithdraw(withdrawId, approved);
        return ResponseEntity.noContent().build();
    }
}
