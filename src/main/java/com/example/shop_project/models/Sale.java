package com.example.shop_project.models;

import jakarta.persistence.*;

@Entity
@Table (name="sales")
public class Sale {
    //Attributes
    @Id
    @Column(name = "product_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantitySaled;

    private double reduction;

    //Default constructor
    public Sale(){
    }

    //Parametrised constructor
    public Sale(Product product, int quantitySaled, double reduction) {
        this.product = product;
        this.quantitySaled = quantitySaled;
        this.reduction = reduction;
    }

    //Getters && Setters
    public Product getProductId() {
        return product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = this.product;
    }

    public int getQuantitySaled() {
        return quantitySaled;
    }

    public void setQuantitySaled(int quantitySaled) {
        this.quantitySaled = quantitySaled;
    }

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    //Print sale details
    @Override
    public String toString(){
        return product.toString()+ "Sale details ->" + "Minimum quantity for sale:" + quantitySaled +
                "reduction given:" + reduction;
    }
}
