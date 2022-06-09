package edu.thejunglegiant.store.service;

import edu.thejunglegiant.store.data.dao.category.ICategoriesDao;
import edu.thejunglegiant.store.data.dao.good.IGoodsDao;
import edu.thejunglegiant.store.data.entity.CategoryEntity;
import edu.thejunglegiant.store.data.entity.GoodEntity;
import edu.thejunglegiant.store.di.DaoSingletonFactory;
import edu.thejunglegiant.store.dto.CatalogFilterDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    private final IGoodsDao goodsDao = DaoSingletonFactory.getInstance().getGoodsDao();
    private final ICategoriesDao categoriesDao = DaoSingletonFactory.getInstance().getCategoriesDao();

    public List<GoodEntity> getAllGoods() {
        return goodsDao.fetchAll(0, -1,-1, -1);
    }

    public List<GoodEntity> getFilteredGoods(CatalogFilterDTO filters) {
        return goodsDao.fetchAll(
                filters.getSortBy() != null ? filters.getSortBy() : 0,
                filters.getCategoryId() != null ? filters.getCategoryId() : -1,
                filters.getPriceFrom() != null ? filters.getPriceFrom() : -1,
                filters.getPriceTo() != null ? filters.getPriceTo() : -1
        );
    }

    public GoodEntity getGoodById(int id) {
        return goodsDao.fetchById(id);
    }

    public List<CategoryEntity> getAllCategories() {
        List<CategoryEntity> res = categoriesDao.fetchAllCategories();
        res.add(0, CategoryEntity.emptyCategory());
        return res;
    }
}
