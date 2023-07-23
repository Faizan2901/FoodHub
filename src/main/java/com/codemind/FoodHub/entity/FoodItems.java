package com.codemind.FoodHub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "fooditems")
public class FoodItems {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "food_price")
    private int foodPrice;


}
