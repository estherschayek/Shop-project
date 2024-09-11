package com.example.shop_project.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name="customers")
public class Customer {

    //Attributes
    @Id
    @GeneratedValue
    private Long id;

    private String fName;

    private String lName;

    private String password;

    private String email;
    private CustomerType custT;

    @Embedded
    private CardDetails cd;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AdressDetails address;

    @OneToMany
    private List<Order> orders= new ArrayList<Order>();

    //Default constructor
    public Customer(){

    }

    //Parametrised constructor
    public Customer(String fName, String lName, String password, String email, CardDetails cd, AdressDetails address, List<Order> orders, CustomerType ct) {
        this.fName = fName;
        this.lName = lName;
        this.password = password;
        this.email = email;
        this.cd = cd;
        this.address = address;
        this.orders = orders;
        this.custT=ct;
    }

    //Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AdressDetails getAddress() {
        return address;
    }

    public void setAddress(AdressDetails address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public CustomerType getCustT() {
        return custT;
    }

    public void setCustT(CustomerType custT) {
        this.custT = custT;
    }

    //Print Customer's details
    @Override
    public String toString(){
        return "Customer [id:" + id + "First Name:" + fName + "Last Name:" + lName +
                "Password:" + password + "Email:" + email + "Adress:" + address+ "Orders:" + orders +"]";
    }


}
