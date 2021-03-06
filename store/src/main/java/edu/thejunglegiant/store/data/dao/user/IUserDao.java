package edu.thejunglegiant.store.data.dao.user;

import edu.thejunglegiant.store.data.entity.UserEntity;

import java.util.List;

public interface IUserDao {

    void createUser(String email, String name, String surname, String passwordHash);

    List<UserEntity> fetchAllUsers(int currentUserId);

    UserEntity findUserById(int id);

    UserEntity findUserByEmail(String email);

    void deleteUserById(int id);

    void updateUser(UserEntity user);
}
