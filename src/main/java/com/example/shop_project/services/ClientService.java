package com.example.shop_project.services;

import com.example.shop_project.exception.CustomException;
import com.example.shop_project.repositories.CustomerRepo;
import com.example.shop_project.repositories.OrderRepo;
import com.example.shop_project.repositories.ProductRepo;
import com.example.shop_project.repositories.SaleRepo;
import org.springframework.beans.factory.annotation.Autowired;



public abstract class ClientService {

    @Autowired
    protected CustomerRepo customerRepo;

    @Autowired
    protected OrderRepo orderRepo;

    @Autowired
    protected ProductRepo productRepo;

    @Autowired
    protected SaleRepo saleRepo;

    public abstract boolean login(String email, String password) throws CustomException;



}
