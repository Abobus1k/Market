package ru.example.megamarket;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.example.megamarket.auth.AuthenticationService;
import ru.example.megamarket.auth.RegisterRequest;

import static ru.example.megamarket.user.Role.ADMIN;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class MegamarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(MegamarketApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());
        };
    }*/
}
