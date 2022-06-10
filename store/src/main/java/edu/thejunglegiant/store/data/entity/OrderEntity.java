package edu.thejunglegiant.store.data.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderEntity {

    private Integer id;

    private Integer userId;

    private Integer status;

    private Timestamp date;
}
