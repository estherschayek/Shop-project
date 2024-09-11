package com.example.shop_project.models;

import jakarta.persistence.*;

@Entity
@Table(name="products")
public class Product {
    //Attributes
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Category category;
    private double price;


    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Sale sale;

//    @OneToOne(mappedBy = "product")
//    private ItemInOrder iteminorder;

    //Default constructor
    public Product(){}

    //Parametrised constructor
    public Product(String name, Category category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    //Getters&Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //Print product details
    @Override
    public String toString(){
        return "Product details->" + "Name:" +name + "Category:" + category + "Price:" + price ;
    }
}
