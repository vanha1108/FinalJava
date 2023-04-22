package com.example.finalJava;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.finalJava.dto.User;
import com.example.finalJava.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setName("Hoa");
        user.setPassword("12334");
        user.setEmail("thaitiendat13@gmail");
        user.setAddress("An Giang");
        user.setPhonenumber("0329001122");

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());

    }

    @Test
    public void testFindByEmail() {
        String email = "johndoe@example.com";
        User user = repo.findByEmail(email);

        assertThat(user.getEmail()).isEqualTo(email);
    }
}