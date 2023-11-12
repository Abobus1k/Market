package ru.example.megamarket.deposit;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.example.megamarket.user.User;
import ru.example.megamarket.user.UserRepository;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final DepositRepository depositRepository;
    private final UserRepository userRepository;
    private final DepositMapper mapper;

    public Deposit addDeposit(DepositRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Deposit deposit = mapper.depositRequestToDeposit(request);
        deposit.setUser(user);
        return depositRepository.save(deposit);
    }

    public void adminDeleteDeposit(Integer depositId, Boolean approved) {
        Deposit deposit = depositRepository.findById(depositId)
                .orElseThrow(() -> new EntityNotFoundException("Депозита с id: " + depositId + " не существует"));

        if (approved) {
            User user = userRepository.findById(deposit.getUser().getId()).orElseThrow();
            user.setBalance(user.getBalance() + deposit.getSum());
            userRepository.save(user);
        }

        depositRepository.delete(deposit);
    }

    public List<Deposit> adminGetAllDeposits(PageRequest pageRequest) {
        return depositRepository.findAll(pageRequest).getContent();
    }

    public Deposit adminGetDeposit(Integer depositId) {
        return depositRepository.findById(depositId)
                .orElseThrow(() -> new EntityNotFoundException("Депозита с id: " + depositId + " не существует"));
    }
}
