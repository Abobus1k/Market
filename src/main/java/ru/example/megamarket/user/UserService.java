package ru.example.megamarket.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public Page<User> getAllUsers(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    public void removeUser(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с id: " + userId + " не существует"));
        repository.delete(user);
    }

    public User userProfile(Integer userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователя с id: " + userId + " не существует"));
    }

    public User userProfile(Principal connectedUser) {
        return  (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
    }

    public User update(UserRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return repository.save(user);
    }

    public void deleteAccount(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        repository.delete(user);
    }
}
