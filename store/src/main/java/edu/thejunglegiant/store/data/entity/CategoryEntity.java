package edu.thejunglegiant.store.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    private Integer id;

    private String title;

    public static CategoryEntity emptyCategory() {
        return new CategoryEntity(-1, "");
    }
}
