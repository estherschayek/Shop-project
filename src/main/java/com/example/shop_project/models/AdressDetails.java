package com.example.shop_project.models;

import jakarta.persistence.*;

@Entity
@Table(name="Adresses_Details")
public class AdressDetails {

    //Attributes

    @Id
    @Column(name = "customer_id")
    private Long id;

    private String city;

    private String street;

    private int number;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;


    //Default constructor
    public AdressDetails(){}

    //Parametrised constructor

    public AdressDetails(String city, String street, int number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }

    //Getters&Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    //Print adress details
    @Override
    public String toString(){
        return "Adress:"+ "city:" + city+  "street:"+ street + number ;
    }
}
