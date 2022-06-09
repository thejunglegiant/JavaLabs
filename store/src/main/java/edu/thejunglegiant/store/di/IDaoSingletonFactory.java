package edu.thejunglegiant.store.di;

import edu.thejunglegiant.store.data.dao.category.ICategoriesDao;
import edu.thejunglegiant.store.data.dao.good.IGoodsDao;
import edu.thejunglegiant.store.data.dao.user.IUserDao;

public interface IDaoSingletonFactory {

    IUserDao getUserDao();

    IGoodsDao getGoodsDao();

    ICategoriesDao getCategoriesDao();
}
