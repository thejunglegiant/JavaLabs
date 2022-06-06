package edu.thejunglegiant.store.service;

import edu.thejunglegiant.store.data.dao.user.IUserDao;
import edu.thejunglegiant.store.data.entity.UserEntity;
import edu.thejunglegiant.store.di.DaoSingletonFactory;
import edu.thejunglegiant.store.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailService")
public class UserDetailService implements UserDetailsService {

    private final IUserDao dao = DaoSingletonFactory.getInstance().getUserDao();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = dao.findUserByEmail(email);

        if (user == null) throw new UsernameNotFoundException("User not found");

        return SecurityUser.fromUser(user);
    }
}
