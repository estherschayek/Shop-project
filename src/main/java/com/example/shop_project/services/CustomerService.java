package com.example.shop_project.services;

import com.example.shop_project.exception.CustomException;
import com.example.shop_project.models.*;
import com.example.shop_project.repositories.ItemInOrderRepo;
import com.example.shop_project.repositories.OrderRepo;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class CustomerService extends ClientService {

    private final ItemInOrderRepo itemInOrderRepo;
    private Long customerID;
    private String name;

    public CustomerService(ItemInOrderRepo itemInOrderRepo) {
        this.itemInOrderRepo = itemInOrderRepo;
    }

    @Override
    public boolean login(String email, String password) throws CustomException {
        if (email != null && password != null) {
            Long id = customerRepo.findCustomerByEmailAndPassword(email, password);


            if (id != null) {
                this.customerID = id;
                Customer c = customerRepo.findCustomerById(customerID);
                this.name = c.getfName() + " " + c.getlName();
            }

            if (customerID > 0 && id != null) {
                System.out.println("Welcome" + name);
            } else {
                throw new CustomException("Email or password are not valid");
            }

        }
        throw new CustomException("Cannot login with empty details");

    }

    public Customer getCustomerDetails() throws CustomException {
        Customer c = customerRepo.findCustomerById(customerID);

        if (c == null) {
            throw new CustomException("Customer doesn't exists");
        }
        return c;

    }


    public List<Product> getProductInOrderByOrderId(Long orderId) throws CustomException {
        if (orderId != null) {

            List<Product> pForCustomByOrder = customerRepo.getProductInOrderById(customerID, orderId);

            if (pForCustomByOrder == null) {
                throw new CustomException(name + ", you don't have any product in this order");
            }
            return pForCustomByOrder;
        }
        throw new CustomException("Order Id can't be null");
    }

    public List<Order> getAllOrdersCustomer() throws CustomException {
        List<Order> myOrders = orderRepo.getAllByCustomerId(customerID);
        if (!myOrders.isEmpty()) {
            return myOrders;
        }

        throw new CustomException(name + ", you didn't command any order");

    }

    public Order getOrderForCustomByDate(Date date) throws CustomException {
        if (date.compareTo(new Date()) <= 0) {
            Order o = orderRepo.getOrderByCustomerIdAndOrderDate(customerID, date);
            if (o == null) {
                throw new CustomException("Sorry, " + name + " you don't have any order at this date");
            }
            return o;
        }
        throw new CustomException("Date doesn't exist yet");
    }

    public void deleteOrderByDate(Date date) throws CustomException {
        Temporal now = (Temporal) new Date();
        if (date != null) {
            long diff = ChronoUnit.DAYS.between(now, (Temporal) date);
            if (diff <= 7) {
                Order o = orderRepo.getOrderByCustomerIdAndOrderDate(customerID, date);
                if (o != null) {
                    orderRepo.delete(o);
                }
                throw new CustomException("Their is no order at that date!");
            }
            throw new CustomException(name + " , sorry! you can't delete this order anymore. Seven days have passed since you have ordered it");

        }
        throw new CustomException("The date can't be empty");

    }


    public void updateOrder(Order order) throws CustomException {
        if (order != null) {
            Order o = orderRepo.getOrderByCustomerIdAndOrderDate(customerID, order.getOrderDate());
            if (o != null) {
                orderRepo.save(order);
            }
            throw new CustomException("Their is no such order at that specific date in order to update it.");
        }
        throw new CustomException("Cannot update empty order");
    }

    public List<Order> addOrder(Order order) throws CustomException {
        if (order != null) {
            List<Order> orders = orderRepo.getAllByCustomerId(customerID);
            orders.add(order);
            orderRepo.save(order);

            System.out.print(name + ", your new list is:");
            return orders;
        }
        throw new CustomException("Cannot add empty order");
    }


    public List<Sale> getAllSales() throws CustomException {
        List<Sale> allSales = saleRepo.findAll();
        if (!allSales.isEmpty()) {
            return allSales;
        }
        throw new CustomException("Actually their is no sales");
    }


    public Sale getSaleForProduct(Long id) throws CustomException {
        if (id != null) {
            Sale s = saleRepo.getSaleForProductId(id);
            if (s != null) {
                return s;
            }
            throw new CustomException("This product is not in sale");
        }
        throw new CustomException("Cannot provide a sale for an empty product");


    }

    public double isOrderContainsItemInSaleAndFinalPriceOrder(Long orderId) throws CustomException {
        if (orderId > 0) {
            Order o = orderRepo.getOrderById(orderId);
            double finalePrice= o.getFinalPrice();
            boolean existSale = saleRepo.isOrderWithSales(orderId);
            if (existSale) {

                List<ItemInOrder> lstItems = o.getItemsInOrder();
                for (int i = 0; i < lstItems.size(); i++) {
                    Sale sForItem=saleRepo.getSaleForItemInOrder(lstItems.get(i).getProduct().getId(), lstItems.get(i).getId());
                    if (sForItem != null) {
                        System.out.println("Great you can have a sale for this item:" + sForItem);
                        lstItems.get(i).setOrderSpecificPrice(lstItems.get(i).getQuantity()* lstItems.get(i).getProduct().getPrice() *(100 - sForItem.getReduction())/100);
                        finalePrice +=lstItems.get(i).getOrderSpecificPrice();
                    } else {
                        System.out.println("The sale for this product is:" + getSaleForProduct(lstItems.get(i).getProduct().getId()) + " you didn't buy enough");
                        lstItems.get(i).setOrderSpecificPrice(lstItems.get(i).getQuantity()* lstItems.get(i).getProduct().getPrice());
                        finalePrice +=lstItems.get(i).getOrderSpecificPrice();
                    }
                }
                o.setFinalPrice(finalePrice);

                return finalePrice;

            }
            throw new CustomException("You can't obtain sale for this order");
        }
        throw new CustomException("Cannot provide sale information for an empty order");
    }

    public List<Product> getProducts() throws CustomException {
        List<Product> pAll = productRepo.findAll();
        if (!pAll.isEmpty()) {
            return pAll;
        }
        throw new CustomException("Their is no product available");
    }

    public Product findProByName(String name) throws CustomException {
        if (name.length() >= 2) {
            Product p = productRepo.findProductByNameContainingIgnoreCase(name);
            if (p != null) {
                return p;
            }
            throw new CustomException("Their is no such product");

        }
        throw new CustomException("Invalid product name");
    }

    public Set<Product> findProductByPrice(double price) throws CustomException {
        if (price > 0) {
            Set<Product> pPrice = productRepo.findProductsByPrice(price);
            if (!pPrice.isEmpty()) {
                return pPrice;
            }
            throw new CustomException("Their is no product at" + price + "shekels");
        }

        throw new CustomException(":) We do not have Free items in our Shop :)");

    }

    public Set<Product> findPByCategory(Category c) throws CustomException {
        if (c != null) {
            Set<Product> pCat = productRepo.findProductsByCategory(c);
            if (!pCat.isEmpty()) {
                return pCat;
            }
            throw new CustomException("Their is no products available at this category");
        }
        throw new CustomException("Category cannot be empty");
    }

    public Set<Product> getProductBySaleEOrA(double reduction) throws CustomException {
        if (reduction > 0) {
            Set<Product> pReduction = productRepo.findProductsBySaleEqualsOrAfter(reduction);
            if (!pReduction.isEmpty()) {
                return pReduction;
            }
            throw new CustomException("Their is no product at this sale or upper. Look for a lower reduction");
        }
        throw new CustomException("Reduction cannot be 0!");
    }

    public void addItemInOrder(ItemInOrder item) throws CustomException{
        if(item!=null){
            itemInOrderRepo.save(item);
        }
        throw new CustomException("Cannot add an empty item");
    }

}

