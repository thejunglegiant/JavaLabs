package edu.thejunglegiant.store.data.dao.good;


import edu.thejunglegiant.store.data.DBConnection;
import edu.thejunglegiant.store.data.entity.GoodEntity;
import edu.thejunglegiant.store.exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GoodsDao implements IGoodsDao {

    private static final DBConnection dbConnection = DBConnection.getInstance();

    private final String FETCH_ALL = "SELECT * FROM goods";

    private GoodEntity buildModel(ResultSet resultSet) throws SQLException {
        GoodEntity model = new GoodEntity();
        model.setId(resultSet.getInt("id"));
        model.setTitle(resultSet.getString("title"));
        model.setPrice(resultSet.getInt("price"));
        model.setColor(resultSet.getString("color"));
        model.setCategoryId(resultSet.getInt("category_id"));
        model.setDate(resultSet.getTimestamp("date"));
        return model;
    }

    @Override
    public List<GoodEntity> fetchAll() {
        ResultSet resultSet;

        List<GoodEntity> resModel = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FETCH_ALL)) {
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resModel.add(buildModel(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(DaoException.DAO_EXCEPTION_MESSAGE, e);
        }

        return resModel;
    }
}
