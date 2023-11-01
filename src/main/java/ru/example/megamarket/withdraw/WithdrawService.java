package ru.example.megamarket.withdraw;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
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

    public void addWithdraw(WithdrawRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (user.getBalance() < request.getSum()) {
            return;
        }

        Withdraw withdraw = mapper.withdrawRequestToWithdraw(request);
        withdraw.setUser(user);
        withdrawRepository.save(withdraw);
    }

    public void adminDeleteWithdraw(Integer withdrawId, Boolean approved) {
        Withdraw withdraw = withdrawRepository.findById(withdrawId).orElseThrow();

        if (approved) {
            User user = userRepository.findById(withdraw.getUser().getId()).orElseThrow();
            user.setBalance(user.getBalance() - withdraw.getSum());
            userRepository.save(user);
        }

        withdrawRepository.delete(withdraw);
    }

    public List<Withdraw> adminGetAllWithdraws() {
        return withdrawRepository.findAll();
    }

    public Withdraw adminGetWithdraw(Integer withdrawId) {
        return withdrawRepository.findById(withdrawId).orElseThrow();
    }
}
