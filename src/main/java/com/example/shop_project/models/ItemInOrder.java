package com.example.shop_project.models;

import jakarta.persistence.*;

@Entity
//To ensure that the product could appear once per order but could appear for different orders
@Table(name = "items_in_order",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "order_id"})})
public class ItemInOrder {
    //Attributes
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product  product;

    private int quantity;
    private double orderSpecificPrice;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order; //Default constructor
    public ItemInOrder(){}

    //Parametrised constructor
    public ItemInOrder(Product productId, int quantity, double orderSpecificPrice) {
        this.product = productId;
        this.quantity = quantity;
        this.orderSpecificPrice = orderSpecificPrice;
    }

    //Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getOrderSpecificPrice() {
        return orderSpecificPrice;
    }

    public void setOrderSpecificPrice(double orderSpecificPrice) {
        this.orderSpecificPrice = orderSpecificPrice;
    }

    //Print item in order 's details
    @Override
    public String toString(){
        return product.toString() + quantity + "Specifique price for this item in this order:" + orderSpecificPrice;
    }
}
