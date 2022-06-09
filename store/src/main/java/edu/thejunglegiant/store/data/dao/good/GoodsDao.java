package edu.thejunglegiant.store.data.dao.good;


import edu.thejunglegiant.store.data.DBConnection;
import edu.thejunglegiant.store.data.entity.GoodEntity;
import edu.thejunglegiant.store.data.entity.UserEntity;
import edu.thejunglegiant.store.exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GoodsDao implements IGoodsDao {

    private static final DBConnection dbConnection = DBConnection.getInstance();

    private final String FETCH_GOOD_BY_ID = "SELECT * FROM goods WHERE id = ? LIMIT 1";

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
    public List<GoodEntity> fetchAll(int orderType, int categoryId, double priceFrom, double priceTo) {
        ResultSet resultSet;
        List<GoodEntity> resModel = new ArrayList<>();

        String query = "SELECT * FROM goods ";

        if (categoryId >= 0) {
            query += "WHERE category_id = %s ".formatted(categoryId);
        }
        if (priceFrom >= 0 && priceTo > priceFrom) {
            query += (categoryId >= 0 ? "AND" : "WHERE") + " price BETWEEN %s AND %s ".formatted(priceFrom, priceTo);
        }
         query += switch (orderType) {
            case 1 -> "ORDER BY title";
            case 2 -> "ORDER BY price DESC";
            case 3 -> "ORDER BY price";
            case 4 -> "ORDER BY date";
            default -> "";
        };

        try (Connection connection = dbConnection.getConnection()) {
            resultSet = connection.createStatement().executeQuery(query);

            while (resultSet.next()) {
                resModel.add(buildModel(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(DaoException.DAO_EXCEPTION_MESSAGE, e);
        }

        return resModel;
    }

    @Override
    public GoodEntity fetchById(int id) {
        ResultSet resultSet;
        GoodEntity res = null;

        try(Connection connection = dbConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(FETCH_GOOD_BY_ID)) {
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
}
