package edu.thejunglegiant.store.di;

import edu.thejunglegiant.store.data.dao.category.CategoriesDao;
import edu.thejunglegiant.store.data.dao.category.ICategoriesDao;
import edu.thejunglegiant.store.data.dao.good.GoodsDao;
import edu.thejunglegiant.store.data.dao.good.IGoodsDao;
import edu.thejunglegiant.store.data.dao.user.IUserDao;
import edu.thejunglegiant.store.data.dao.user.UserDao;

public class DaoSingletonFactory implements IDaoSingletonFactory {

    private static final IDaoSingletonFactory factory = new DaoSingletonFactory();

    private final IUserDao userDao = new UserDao();
    private final IGoodsDao goodsDao = new GoodsDao();
    private final ICategoriesDao categoriesDao = new CategoriesDao();

    public static IDaoSingletonFactory getInstance() {
        return factory;
    }

    @Override
    public IUserDao getUserDao() {
        return userDao;
    }

    @Override
    public IGoodsDao getGoodsDao() {
        return goodsDao;
    }

    @Override
    public ICategoriesDao getCategoriesDao() {
        return categoriesDao;
    }
}
