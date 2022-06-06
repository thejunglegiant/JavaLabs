package edu.thejunglegiant.store.data.dao.user;

import edu.thejunglegiant.store.data.DBConnection;
import edu.thejunglegiant.store.data.entity.UserEntity;
import edu.thejunglegiant.store.exceptions.DaoException;
import edu.thejunglegiant.store.security.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao implements IUserDao {

    private static final DBConnection dbConnection = DBConnection.getInstance();

    private final String FETCH_ALL_USERS = "SELECT * FROM users";

    private final String FETCH_USER_BY_ID = "SELECT * FROM users WHERE id = ? LIMIT 1";

    private final String FETCH_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ? LIMIT 1";

    private UserEntity buildModel(ResultSet resultSet) throws SQLException {
        UserEntity model = new UserEntity();
        model.setId(resultSet.getInt("id"));
        model.setEmail(resultSet.getString("email"));
        model.setPassword(resultSet.getString("password"));
        model.setFirstName(resultSet.getString("first_name"));
        model.setLastName(resultSet.getString("last_name"));
        model.setRole(resultSet.getString("role"));
        model.setIsActive(resultSet.getBoolean("is_active"));
        return model;
    }

    @Override
    public List<UserEntity> fetchAllUsers() {
        ResultSet resultSet;

        List<UserEntity> res = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(FETCH_ALL_USERS)) {
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                res.add(buildModel(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(DaoException.DAO_EXCEPTION_MESSAGE, e);
        }

        return res;
    }

    @Override
    public UserEntity findUserById(int id) {
        ResultSet resultSet;
        UserEntity res = null;

        try(Connection connection = dbConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(FETCH_USER_BY_ID)) {
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                res = buildModel(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(DaoException.DAO_EXCEPTION_MESSAGE, e);
        }

        return res;
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        ResultSet resultSet;
        UserEntity res = null;

        try(Connection connection = dbConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(FETCH_USER_BY_EMAIL)) {
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                res = buildModel(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(DaoException.DAO_EXCEPTION_MESSAGE, e);
        }

        return res;
    }

    @Override
    public void deleteUserById(int id) {

    }

    @Override
    public void updateUser(UserEntity user) {

    }
}
