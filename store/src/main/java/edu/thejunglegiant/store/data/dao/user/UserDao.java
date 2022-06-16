package edu.thejunglegiant.store.data.dao.user;

import edu.thejunglegiant.store.data.DBConnection;
import edu.thejunglegiant.store.data.entity.UserEntity;
import edu.thejunglegiant.store.exceptions.DaoException;
import edu.thejunglegiant.store.security.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao {

    private static final DBConnection dbConnection = DBConnection.getInstance();

    private final String FETCH_USER_BY_ID = "SELECT * FROM users WHERE id = ? LIMIT 1";

    private final String FETCH_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ? LIMIT 1";

    private UserEntity buildModel(ResultSet resultSet) throws SQLException {
        UserEntity model = new UserEntity();
        model.setId(resultSet.getInt("id"));
        model.setEmail(resultSet.getString("email"));
        model.setPassword(resultSet.getString("password"));
        String firstname = resultSet.getString("first_name");
        model.setFirstName(firstname == null ? "Unknown" : firstname);
        String lastname = resultSet.getString("last_name");
        model.setLastName(lastname == null ? "User" : lastname);
        model.setRole(resultSet.getString("role"));
        model.setIsActive(resultSet.getBoolean("is_active"));
        return model;
    }

    @Override
    public void createUser(String email, String name, String surname, String passwordHash) {
        String query = "INSERT INTO users (email, first_name, last_name, role, is_active, password) VALUES ('%s', '%s', '%s', '%s', true, '%s')"
                .formatted(email, name, surname, UserRole.USER.name(), passwordHash);

        try (Connection connection = dbConnection.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new DaoException(DaoException.DAO_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<UserEntity> fetchAllUsers(int currentUserId) {
        ResultSet resultSet;
        List<UserEntity> res = new ArrayList<>();

        String query = "SELECT * FROM users WHERE id != %s AND is_active = true".formatted(currentUserId);

        try (Connection connection = dbConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
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

        try (Connection connection = dbConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(FETCH_USER_BY_ID)) {
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

        try (Connection connection = dbConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(FETCH_USER_BY_EMAIL)) {
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
        String query = "UPDATE users SET is_active = false WHERE id = %s".formatted(id);

        try (Connection connection = dbConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(DaoException.DAO_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void updateUser(UserEntity user) {

    }
}
