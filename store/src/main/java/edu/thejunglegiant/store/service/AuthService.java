package edu.thejunglegiant.store.service;

import edu.thejunglegiant.store.data.dao.user.IUserDao;
import edu.thejunglegiant.store.data.entity.UserEntity;
import edu.thejunglegiant.store.di.DaoSingletonFactory;
import edu.thejunglegiant.store.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final IUserDao userDao = DaoSingletonFactory.getInstance().getUserDao();
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String authorize(String email, String password) throws AuthenticationException, UsernameNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        UserEntity user = userDao.findUserByEmail(email);

        if (user == null) throw new UsernameNotFoundException("User not found");

        return jwtTokenProvider.createToken(email, user.getRole());
    }

    public String register(String email, String name, String surname, String password) throws AuthenticationException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String passwordHash = encoder.encode(password);
        userDao.createUser(email, name, surname, passwordHash);

        return authorize(email, password);
    }
}
