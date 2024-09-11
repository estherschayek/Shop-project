package com.example.shop_project.models;

import jakarta.persistence.Embeddable;

import java.util.Date;

@Embeddable
public class CardDetails {
    //Attributes
    private String num;
    private Date expirationDate;
    private int cvv;

    //Default constructor
    public CardDetails(){}

    //Parametrised constructor

    public CardDetails(String num, Date expirationDate, int cvv) {
        this.num = num;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    //Getters & Setters

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    //Print card details
    @Override
    public String toString(){
        return "Card Details:"+ "Card number->" + num + "Expiration date->" + expirationDate + "CVV" + cvv;
    }
}
