package edu.thejunglegiant.store.service;

import edu.thejunglegiant.store.data.dao.good.IGoodsDao;
import edu.thejunglegiant.store.data.entity.GoodEntity;
import edu.thejunglegiant.store.di.DaoSingletonFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    private final IGoodsDao dao = DaoSingletonFactory.getInstance().getGoodsDao();

    public List<GoodEntity> getAllGoods(int orderType, int priceFrom, int priceTo) {
        return dao.fetchAll(orderType, priceFrom, priceTo);
    }

    public GoodEntity getGoodById(int id) {
        return dao.fetchById(id);
    }
}
