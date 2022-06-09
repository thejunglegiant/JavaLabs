package edu.thejunglegiant.store.data.dao.category;

import edu.thejunglegiant.store.data.entity.CategoryEntity;

import java.util.List;

public interface ICategoriesDao {

    List<CategoryEntity> fetchAllCategories();
}
