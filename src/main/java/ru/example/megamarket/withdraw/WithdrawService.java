package ru.example.megamarket.withdraw;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.example.megamarket.exceptions.localexceptions.InsufficientFundsException;
import ru.example.megamarket.user.User;
import ru.example.megamarket.user.UserRepository;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawService {

    private final WithdrawRepository withdrawRepository;
    private final UserRepository userRepository;
    private final WithdrawMapper mapper;

    public Withdraw addWithdraw(WithdrawRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (user.getBalance() < request.getSum()) {
            throw new InsufficientFundsException("Недостаточно средст для покупки");
        }

        Withdraw withdraw = mapper.withdrawRequestToWithdraw(request);
        withdraw.setUser(user);
        return withdrawRepository.save(withdraw);
    }

    public void adminDeleteWithdraw(Integer withdrawId, Boolean approved) {
        Withdraw withdraw = withdrawRepository.findById(withdrawId)
                .orElseThrow(() -> new EntityNotFoundException("Заявки на вывод средств с id: " + withdrawId + " не существует"));

        if (approved) {
            User user = userRepository.findById(withdraw.getUser().getId()).orElseThrow();
            user.setBalance(user.getBalance() - withdraw.getSum());
            userRepository.save(user);
        }

        withdrawRepository.delete(withdraw);
    }

    public List<Withdraw> adminGetAllWithdraws(PageRequest pageRequest) {
        return withdrawRepository.findAll(pageRequest).getContent();
    }

    public Withdraw adminGetWithdraw(Integer withdrawId) {
        return withdrawRepository.findById(withdrawId)
                .orElseThrow(() -> new EntityNotFoundException("Заявки на вывод средств с id: " + withdrawId + " не существует"));
    }
}
