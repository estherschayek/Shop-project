package com.example.shop_project.repositories;

import com.example.shop_project.models.Customer;
import com.example.shop_project.models.CustomerType;
import com.example.shop_project.models.Order;
import com.example.shop_project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface CustomerRepo extends JpaRepository<Customer,Long> {

    public boolean existsByEmailAndId(String email,Long id);

    public Customer findCustomerById(Long id);

    @Query("select c.id from Customer c where c.email=?1 and c.password=?2")
    public Long findCustomerByEmailAndPassword(String email, String password);

    @Query("select c from Customer c where c.email=?1 and c.id=?2 ")
    public Customer findCustomerByEmailAndId(String email,Long id);


    @Query(value = "SELECT c.id, c.fName, c.lName , SUM(io.quantity) " +
             "FROM customers c " +
            "JOIN orders o ON c.id = o.customer_id " +
            "JOIN items_in_order io ON o.id = io.order_id " +
            "GROUP BY c.id, c.fName, c.lName " +
            "HAVING SUM(io.quantity) >=?max2", nativeQuery = true)
    List<Object[]> getCustomersWithTotalItemQuantityGreaterThan( int max2);

    @Query("select o from Customer c join c.orders o  where c.id=?1 and o.id=?2  ")
    public Order findSpecificOrderOfCustomerByCustomIdAndDate(Long custId, Date orderIds);

    @Query("select c.orders from Customer c where c.id=?1 ")
    public List<Order> findCustomerOrders(Long id);

    @Query("select i.product from Customer c join c.orders o join o.itemsInOrder i where c.id=?1 and o.id=?2")
    public List<Product> getProductInOrderById(Long customId, Long orderId);

    @Query("select c.id, c.orders from Customer c ")
    public List<Object[]> getAllOrdersOfAllCustomers();






}
