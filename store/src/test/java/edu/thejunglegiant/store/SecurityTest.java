package edu.thejunglegiant.store;

import edu.thejunglegiant.store.security.JwtTokenProvider;
import edu.thejunglegiant.store.security.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityTest {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Test
    public void encryptionTest() {
        String password = "1111";
        String passwordHash = encoder.encode(password);

        assertTrue(encoder.matches(password, passwordHash));
    }

    @Test
    public void tokenTest() {
        String email = "test@mail.com";
        String role = UserRole.USER.name();

        String token = tokenProvider.createToken(email, role);

        assertEquals(email, JwtTokenProvider.getUserEmail(token));
    }
}
