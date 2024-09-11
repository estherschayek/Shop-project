package com.example.shop_project.services;

import com.example.shop_project.exception.CustomException;
import com.example.shop_project.models.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService extends ClientService{

    public AdminService(){}

    @Override
    public boolean login(String email, String password) throws CustomException {
        if(email!=null && password!=null){
            if(email.equals("admin@gmail.com") && password.equals("admin")){
                System.out.println("Welcome administrator!");
                return true;
            }
        }
        throw new CustomException("Invalid email or password");
    }


    public void addCustomer(Customer customer) throws CustomException {
        if(customer != null) {
            if(!customerRepo.existsByEmailAndId(customer.getEmail(), customer.getId())) {
                customerRepo.save(customer);
            }
            else {
                throw new CustomException("Cannot add customer - this customer already exist in the system");
            }
        } else {
            throw new CustomException("Cannot add empty customer");
        }
    }

    public void updateCustomer(Customer customer) throws CustomException {
        if(customer != null) {
            if(customerRepo.findCustomerById(customer.getId()) != null) {
                if(customerRepo.findCustomerByEmailAndId(customer.getEmail(),customer.getId()) != null) {
                    throw new CustomException("Customer email is already exists in another customer");
                }
                customerRepo.save(customer);
            }
            else {
                throw new CustomException("The ID does not exist in the system");
            }
        } else {
            throw new CustomException("Cannot update empty Customer");
        }
    }

    public void deleteCustomer(Long customerID) throws CustomException {
        if(customerID > 0) {
            Customer c = customerRepo.findCustomerById(customerID);
            if(c == null) {
                throw new CustomException("No customer found with this specific ID");
            }
            else {

                customerRepo.delete(c);

            }
        } else {
            throw new CustomException("cannot delete customer with invalid ID");
        }
    }

    public List<Customer> getAllCustomers() throws CustomException {
        if((customerRepo.findAll().isEmpty())) {
            throw new CustomException("There are no customers in the system");
        }
        return (List<Customer>)customerRepo.findAll();
    }


    public Customer getOneCustomer(Long customerID) throws CustomException {
        if(customerID > 0) {
            Customer c = customerRepo.findCustomerById(customerID);
            if(c == null) {
                throw new CustomException("No customer found with this specific ID");
            }
            return c;
        }
        else {
            throw new CustomException("Cannot show customer with invalid ID");
        }
    }


    public List<Object[]> getCustomerQuantityGreaterThan(int q) throws CustomException {
        if( customerRepo.getCustomersWithTotalItemQuantityGreaterThan(q).isEmpty()){
            throw new CustomException("Their is no customers that ordered more than {q} products");
        }
        else
            return customerRepo.getCustomersWithTotalItemQuantityGreaterThan(q);
    }

    public Map<Long,List<Order>> getAllOrdersOfAllCustomers() throws CustomException {
       List<Object[]> results=customerRepo.getAllOrdersOfAllCustomers();
        if( results.isEmpty()){
            throw new CustomException ("No order present in the system");
        }

        Map<Long,List<Order>> mapOrder= new HashMap<>();

        for(Object[] result : results) {
            Long customerId = (Long) result[0];
            List<Order> orders= (List<Order>) result[1];
            mapOrder.put(customerId,orders);

        }
        return mapOrder;

    }


    public List<Order> getCustomerOrderById(Long id) throws CustomException{
        List<Order> customOrders= customerRepo.findCustomerOrders(id);
        if( customOrders.isEmpty()){
            throw new CustomException("This customer doesn't have any order yet");
        }
        return customOrders;
    }

    public Order getSpecificOrderByCustomIdAndDate(Long id, Date date) throws CustomException{
        if(customerRepo.existsById(id)){
            Order orderSpe=customerRepo.findSpecificOrderOfCustomerByCustomIdAndDate(id,date);
            if(orderSpe==null){
                throw new CustomException("This customer doesn't have any order at this specific date");
            }
            return orderSpe;
        }
        throw new CustomException("The id is incorrect or this customer doesn't exists on the system");
    }


    public List<Order> getOrdersByCustomerIdAndFinalPrice(Long id,double fPrice) throws CustomException{
        List<Order> orderPrime=orderRepo.getAllByCustomerIdAndFinalPriceAfter(id,fPrice);
        if(!orderPrime.isEmpty()){
            if(orderPrime.size()>5){
                System.out.println("The customer with id:"+id+"can have a Prime of 100$ on your next buy");
                return orderPrime;
            }
            else
                throw new CustomException("The customer with id:"+id+"can't get a prime because the amount of order near to "+fPrice+"is" + orderPrime.size()+ "-" +"smaller than 5");


        }
        throw new CustomException("Their is no order near this price for the specific customer");
    }
    public List<Order> getOrdersByOrderDateBetween(Date d1, Date d2) throws CustomException{
        Date dateNow= new Date();
        if(d1!=null && d2!=null){
            if(d1.compareTo(dateNow)<0 && d2.compareTo(dateNow)<0 || d2.compareTo(dateNow)==0 ){
                List<Order> lst=orderRepo.getAllByOrderDateBetween(d1,d2);
                if(!lst.isEmpty()){
                    return lst;
                }
                throw new CustomException("Their is no order between"+ d1 +"and"+ d2);
            }
            throw new CustomException("Date can't be greater than today's date");
        }
        throw new CustomException("Date field can't be empty!");
    }

    public void addSale(Sale sale) throws CustomException{
        if(sale!=null){
            saleRepo.save(sale);

        }
        throw new CustomException("Cannot add an empty sale");
    }

    public List<Sale> getAllSale() throws CustomException{
        if(saleRepo.findAll().isEmpty()){

            throw new CustomException("Cannot add an empty sale");
        }
        return(List<Sale>)saleRepo.findAll();

    }


    public void updateSale(Sale sale) throws CustomException {
       if(saleRepo.existsSalesByIdAndProductId(sale.getId(),sale.getProductId().getId())){
           saleRepo.save(sale);
       }
       throw new CustomException("No such Sale to update it.");
    }

    public void deleteSale(Sale s) throws CustomException{
        if(s!=null){
            saleRepo.delete(s);
        }
        throw new CustomException("Cannot delete an empty sale");
    }

    public List<Product> getProducts() throws CustomException{
        List<Product> p= productRepo.findAll();
        if(!p.isEmpty()){
            return p;
        }
        throw new CustomException("Their is no product available");
    }

    public void addProduct(Product p) throws CustomException{
        if(p!=null){
            productRepo.save(p);
        }
        throw new CustomException("The product cannot be empty");
    }

    public void deleteProduct(Product p) throws CustomException{
        if(p!=null){
            productRepo.delete(p);
        }
        throw new CustomException("Cannot delete an none existing product");
    }

    public void updateProduct(Product p) throws CustomException{
        if(productRepo.existsById(p.getId())){
            productRepo.save(p);
        }
        throw new CustomException("This product doesn't exist in the system - you can't update it!");

    }
}
