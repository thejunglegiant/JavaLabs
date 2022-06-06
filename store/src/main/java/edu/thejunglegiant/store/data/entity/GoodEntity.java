package edu.thejunglegiant.store.data.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class GoodEntity {

    private Integer id;

    private String title;

    private Integer price;

    private Timestamp date;

    private Integer categoryId;

    private String color;
}
