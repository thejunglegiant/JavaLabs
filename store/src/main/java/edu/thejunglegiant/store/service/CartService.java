package edu.thejunglegiant.store.service;

import edu.thejunglegiant.store.data.dao.order.IOrdersDao;
import edu.thejunglegiant.store.data.dao.user.IUserDao;
import edu.thejunglegiant.store.data.entity.GoodEntity;
import edu.thejunglegiant.store.data.entity.UserEntity;
import edu.thejunglegiant.store.di.DaoSingletonFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final IOrdersDao ordersDao = DaoSingletonFactory.getInstance().getOrdersDao();
    private final IUserDao userDao = DaoSingletonFactory.getInstance().getUserDao();

    public List<GoodEntity> showCart(String email) {
        UserEntity user = userDao.findUserByEmail(email);
        return ordersDao.getAllCartGoods(user.getId());
    }

    public void makeCartPayment(String email) {
        UserEntity user = userDao.findUserByEmail(email);
        ordersDao.payCart(user.getId());
    }

    public void addGoodToCart(String email, int goodId) {
        UserEntity user = userDao.findUserByEmail(email);
        ordersDao.addGoodToCart(user.getId(), goodId);
    }

    public void removeGoodFromCart(String email, int goodId) {
        UserEntity user = userDao.findUserByEmail(email);
        ordersDao.removeGoodFromCart(user.getId(), goodId);
    }
}
