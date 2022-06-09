package edu.thejunglegiant.store.data.dao.category;

import edu.thejunglegiant.store.data.DBConnection;
import edu.thejunglegiant.store.data.entity.CategoryEntity;
import edu.thejunglegiant.store.exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDao implements ICategoriesDao {

    private static final DBConnection dbConnection = DBConnection.getInstance();

    private final String FETCH_ALL_CATEGORIES = "SELECT * FROM categories";

    private CategoryEntity buildModel(ResultSet resultSet) throws SQLException {
        CategoryEntity model = new CategoryEntity();
        model.setId(resultSet.getInt("id"));
        model.setTitle(resultSet.getString("title"));
        return model;
    }

    @Override
    public List<CategoryEntity> fetchAllCategories() {
        ResultSet resultSet;
        List<CategoryEntity> resModel = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(FETCH_ALL_CATEGORIES)) {
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                resModel.add(buildModel(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(DaoException.DAO_EXCEPTION_MESSAGE, e);
        }

        return resModel;
    }
}
