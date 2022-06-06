package edu.thejunglegiant.store.data.dao.good;

import edu.thejunglegiant.store.data.entity.GoodEntity;

import java.util.List;

public interface IGoodsDao {

    List<GoodEntity> fetchAll();
}