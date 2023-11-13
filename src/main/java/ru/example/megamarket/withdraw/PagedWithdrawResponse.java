package ru.example.megamarket.withdraw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagedWithdrawResponse {
    private Integer totalPages;
    private List<WithdrawResponse> withdrawResponseList;
}
