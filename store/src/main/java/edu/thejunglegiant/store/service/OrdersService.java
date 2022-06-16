package edu.thejunglegiant.store.service;

import edu.thejunglegiant.store.data.dao.order.IOrdersDao;
import edu.thejunglegiant.store.data.entity.OrderEntity;
import edu.thejunglegiant.store.di.DaoSingletonFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

    IOrdersDao ordersDao = DaoSingletonFactory.getInstance().getOrdersDao();

    public List<OrderEntity> fetchOrders() {
        return ordersDao.fetchAllOrders();
    }
}
