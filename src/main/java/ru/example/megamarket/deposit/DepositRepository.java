package ru.example.megamarket.deposit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Integer> {
}
