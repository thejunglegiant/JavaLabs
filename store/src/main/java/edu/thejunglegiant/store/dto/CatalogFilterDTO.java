package edu.thejunglegiant.store.dto;

import lombok.Data;

@Data
public class CatalogFilterDTO {

    private Integer sortBy;

    private Integer categoryId;

    private Double priceFrom;

    private Double priceTo;
}
