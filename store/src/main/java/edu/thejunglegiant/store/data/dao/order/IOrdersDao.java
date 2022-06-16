package edu.thejunglegiant.store.data.dao.order;

import edu.thejunglegiant.store.data.entity.GoodEntity;
import edu.thejunglegiant.store.data.entity.OrderEntity;

import java.util.List;

public interface IOrdersDao {

    List<OrderEntity> fetchOrdersByUserId(int userId);

    List<OrderEntity> fetchAllOrders();

    void addGoodToCart(int userId, int goodId);

    void removeGoodFromCart(int userId, int goodId);

    List<GoodEntity> getAllCartGoods(int userId);

    void payCart(int userId);
}
