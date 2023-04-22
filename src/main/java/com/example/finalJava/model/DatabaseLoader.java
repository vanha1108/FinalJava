package com.example.finalJava.model;


import com.example.finalJava.Repository.UserRepository;
import com.example.finalJava.dto.Role;
import com.example.finalJava.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseLoader {
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DatabaseLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public CommandLineRunner initializeDatabase() {
        return args -> {
            User user1 = new User(1, "admin", passwordEncoder.encode("123456"), "admin123@gmail.com", "hcm", "0321111333", Role.ADMIN);

            userRepository.save(user1);
            System.out.println("Sample database initialized");
        };
    }
}
