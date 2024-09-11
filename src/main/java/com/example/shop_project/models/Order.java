package com.example.shop_project.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {

    //Attributes
    @Id
    @GeneratedValue
    private Long id;
    private Date orderDate;
    private double finalPrice;
    @OneToMany
    private List<ItemInOrder> itemsInOrder;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    //Default constructor
    public Order(){}

    //Parametrised constructor
    public Order(Date orderDate, double finalPrice, List<ItemInOrder> itemsInOrder) {
        this.orderDate = orderDate;
        this.finalPrice = finalPrice;
        this.itemsInOrder = itemsInOrder;
    }

    //Getters& Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public List<ItemInOrder> getItemsInOrder() {
        return itemsInOrder;
    }

    public void setItemsInOrder(List<ItemInOrder> itemsInOrder) {
        this.itemsInOrder = itemsInOrder;
    }
    //Print order details
    @Override
    public String toString(){
        return "Order details:" + "Order date:" + orderDate + "Final price:" +
                finalPrice + "Items in order:" + itemsInOrder;
    }
}
