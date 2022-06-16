package edu.thejunglegiant.store.data.dao.order;

import edu.thejunglegiant.store.data.DBConnection;
import edu.thejunglegiant.store.data.dao.good.GoodsDao;
import edu.thejunglegiant.store.data.entity.GoodEntity;
import edu.thejunglegiant.store.data.entity.OrderEntity;
import edu.thejunglegiant.store.exceptions.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrdersDao implements IOrdersDao {

    private static final DBConnection dbConnection = DBConnection.getInstance();

    public static OrderEntity buildModel(ResultSet resultSet) throws SQLException {
        OrderEntity model = new OrderEntity();
        model.setId(resultSet.getInt("id"));
        model.setUserId(resultSet.getInt("user_id"));
        model.setStatus(resultSet.getInt("status"));
        model.setDate(resultSet.getTimestamp("date"));
        return model;
    }

    @Override
    public List<OrderEntity> fetchOrdersByUserId(int userId) {
        ResultSet resultSet;
        List<OrderEntity> resModel = new ArrayList<>();

        String query = "SELECT * FROM orders WHERE user_id = %s AND status > 0".formatted(userId);

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
    public List<OrderEntity> fetchAllOrders() {
        ResultSet resultSet;
        List<OrderEntity> resModel = new ArrayList<>();

        String query = "SELECT * FROM orders";

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
    public void addGoodToCart(int userId, int goodId) {
        String query = "SELECT * FROM orders WHERE user_id = %s AND status = 0 LIMIT 1".formatted(userId);

        try (Connection connection = dbConnection.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet cartStatement;
            cartStatement = stmt.executeQuery(query);

            long cartId = -1;
            if (cartStatement.next()) {
                cartId = buildModel(cartStatement).getId();
            } else {
                String key[] = {"id"};
                query = "INSERT INTO orders (user_id, status) VALUES ('%s', '0')".formatted(userId);
                stmt.executeUpdate(query, key);
                cartStatement = stmt.getGeneratedKeys();

                if (cartStatement.next()) {
                    cartId = cartStatement.getLong(1);
                }
            }

            if (cartId != -1) {
                query = "INSERT INTO goods_orders (order_id, good_id) VALUES ('%s', '%s')".formatted(cartId, goodId);
                stmt.executeUpdate(query);
            }
        } catch (SQLException e) {
            throw new DaoException(DaoException.DAO_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public void removeGoodFromCart(int userId, int goodId) {
        String query = "SELECT * FROM orders WHERE user_id = %s AND status = 0 LIMIT 1".formatted(userId);

        try (Connection connection = dbConnection.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet cartStatement;
            cartStatement = stmt.executeQuery(query);

            if (cartStatement.next()) {
                long cartId = buildModel(cartStatement).getId();
                query = "DELETE FROM goods_orders WHERE order_id = %s AND good_id = %s".formatted(cartId, goodId);
                stmt.executeUpdate(query);
            }
        } catch (SQLException e) {
            throw new DaoException(DaoException.DAO_EXCEPTION_MESSAGE, e);
        }
    }

    @Override
    public List<GoodEntity> getAllCartGoods(int userId) {
        ResultSet resultSet;
        List<GoodEntity> resModel = new ArrayList<>();

        String query = "SELECT goods.* FROM orders\n" +
                "LEFT JOIN goods_orders ON goods_orders.order_id = orders.id\n" +
                "LEFT JOIN goods ON goods.id = goods_orders.good_id\n" +
                "WHERE orders.user_id = %s AND orders.status = 0".formatted(userId);

        try (Connection connection = dbConnection.getConnection()) {
            resultSet = connection.createStatement().executeQuery(query);

            while (resultSet.next()) {
                resModel.add(GoodsDao.buildModel(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(DaoException.DAO_EXCEPTION_MESSAGE, e);
        }

        return resModel;
    }

    @Override
    public void payCart(int userId) {
        String query = "UPDATE orders SET status = 1 WHERE user_id = %s AND status = 0".formatted(userId);

        try (Connection connection = dbConnection.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new DaoException(DaoException.DAO_EXCEPTION_MESSAGE, e);
        }
    }
}
