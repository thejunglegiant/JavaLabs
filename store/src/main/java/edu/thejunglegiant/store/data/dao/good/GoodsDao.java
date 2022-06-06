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

    private final String FETCH_ALL_GOODS = "SELECT * FROM goods";

    private final String FETCH_ALL_GOODS_ORDER_BY_NAME = "SELECT * FROM goods ORDER BY title";

    private final String FETCH_ALL_GOODS_ORDER_BY_PRICE = "SELECT * FROM goods ORDER BY price DESC";

    private final String FETCH_ALL_GOODS_ORDER_BY_PRICE_LOW = "SELECT * FROM goods ORDER BY price";

    private final String FETCH_ALL_GOODS_FILTERED_BY_PRICE = "SELECT * FROM goods WHERE price BETWEEN ? AND ?";

    private final String FETCH_ALL_GOODS_ORDER_BY_DATE = "SELECT * FROM goods ORDER BY date";

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
    public List<GoodEntity> fetchAll(int orderType, int priceFrom, int priceTo) {
        ResultSet resultSet;
        List<GoodEntity> resModel = new ArrayList<>();

        String query = switch (orderType) {
            case 1 -> FETCH_ALL_GOODS_ORDER_BY_NAME;
            case 2 -> FETCH_ALL_GOODS_ORDER_BY_PRICE;
            case 3 -> FETCH_ALL_GOODS_ORDER_BY_PRICE_LOW;
            case 4 -> FETCH_ALL_GOODS_ORDER_BY_DATE;
            default -> FETCH_ALL_GOODS;
        };
        if (priceFrom >= 0 && priceTo >= 0) {
            query = FETCH_ALL_GOODS_FILTERED_BY_PRICE;
        }

        try (Connection connection = dbConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            if (priceFrom >= 0 && priceTo >= 0) {
                statement.setInt(1, priceFrom);
                statement.setInt(2, priceTo);
            }
            resultSet = statement.executeQuery();

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
