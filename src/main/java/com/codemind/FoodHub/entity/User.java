package com.codemind.FoodHub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled")
    private boolean enabled;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @ManyToMany(fetch =FetchType.EAGER,cascade = CascadeType.ALL)
            @JoinTable(name = "food_items_user_list",
            joinColumns = @JoinColumn(name = "user_list_id"),
            inverseJoinColumns = @JoinColumn(name = "food_items_id"))
    List<FoodItems> foodItems;


    public List<FoodItems> add(FoodItems tempFoodItem){

        if(foodItems == null){
            foodItems = new ArrayList<>();
        }

        foodItems.add(tempFoodItem);

        return foodItems;
    }

    /*OneToMany: LAZY
    ManyToOne: EAGER
    ManyToMany: LAZY
    OneToOne: EAGER*/


}
