package com.example.shop_project.repositories;

import com.example.shop_project.models.ItemInOrder;
import com.example.shop_project.models.Order;
import com.example.shop_project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {


//    Customer
    public List<Order> getAllByCustomerId(Long customer_id);

//    Customer
    public Order getOrderByCustomerIdAndOrderDate(Long id, Date date);

//    Admin -in order to have special sale
    public List<Order> getAllByCustomerIdAndFinalPriceAfter(Long id,double finalPrice);

//    Admin
    public List<Order> getAllByOrderDateBetween(Date d1, Date d2);

  public Order getOrderById(Long id);











}
