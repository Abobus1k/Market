package ru.example.megamarket.deposit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagedDepositResponse {
    private Integer totalPages;
    private List<DepositResponse> depositResponseList;
}
