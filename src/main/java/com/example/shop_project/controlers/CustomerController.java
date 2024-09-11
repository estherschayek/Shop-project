package com.example.shop_project.controlers;

import com.example.shop_project.exception.CustomException;
import com.example.shop_project.jwt.AuthenticationRequest;
import com.example.shop_project.jwt.AuthenticationResponse;
import com.example.shop_project.jwt.JwtUtils;
import com.example.shop_project.models.*;

import com.example.shop_project.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController extends ClientController{

    @Autowired
    private JwtUtils jwtUtils;

    private CustomerService customerService;

    public CustomerController(){

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws CustomException {
        customerService = (CustomerService) loginManager.login(authenticationRequest.getEmail(), authenticationRequest.getPassword(), CustomerType.Customer);
        if(customerService != null) {
      String token = jwtUtils.generateToken(authenticationRequest, CustomerType.Administrator);
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } else {
            return new ResponseEntity<String>("Invalid Email or Password...", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/getCustomerDetails")
    public Customer getCustomerDetails() throws CustomException{
       return customerService.getCustomerDetails();
    }

    @GetMapping("/getProductInOrderByOrderId/{id}")
    public List<Product> getProductInOrderByOrderId(@PathVariable Long orderId) throws CustomException{
        return customerService.getProductInOrderByOrderId(orderId);
    }

    @GetMapping("/getAllOrdersCustomer")
    public List<Order> getAllOrdersCustomer() throws CustomException{
        return customerService.getAllOrdersCustomer();
    }

    @GetMapping("/getOrderForCustomByDate/{date}")
    public Order getOrderForCustomByDate(@PathVariable Date date) throws CustomException{
        return customerService.getOrderForCustomByDate(date);
    }

    @DeleteMapping("/deleteOrderByDate/{date}")
    public void deleteOrderByDate(@PathVariable Date date) throws CustomException{
        customerService.deleteOrderByDate(date);
    }

    @PutMapping("/updateOrder")
    public void updateOrder(@RequestBody Order order) throws CustomException{
        customerService.updateOrder(order);
    }

    @PostMapping("/addOrder")
    public List<Order> addOrder(@RequestBody Order order) throws CustomException{
        return customerService.addOrder(order);
    }

    @GetMapping("/getAllSales")
    public List<Sale> getAllSales() throws CustomException{
        return customerService.getAllSales();
    }

    @GetMapping("/getSaleForProduct/{id}")
    public Sale getSaleForProduct(@PathVariable Long id) throws CustomException{
        return customerService.getSaleForProduct(id);
    }


    @GetMapping("/isOrderContainsItemInSaleAndFinalPriceOrder/{id}")
    public double isOrderContainsItemInSaleAndFinalPriceOrder(@PathVariable Long orderId) throws CustomException {
        return customerService.isOrderContainsItemInSaleAndFinalPriceOrder(orderId);
    }

    @GetMapping("/getProducts")
    public List<Product> getProducts() throws CustomException{
        return customerService.getProducts();
    }

    @GetMapping("/findProByName")
    public Product findProByName(@RequestBody String name) throws CustomException{
        return customerService.findProByName(name);
    }

    @GetMapping("/findProductByPrice")
    public Set<Product> findProductByPrice(@RequestBody double price) throws CustomException{
        return customerService.findProductByPrice(price);
    }

    @GetMapping("/findPByCategory/{c}")
    public Set<Product> findPByCategory(@PathVariable Category c) throws CustomException{
        return customerService.findPByCategory(c);
    }

    @GetMapping("/getProductBySaleEOrA")
    public Set<Product> getProductBySaleEOrA(@RequestBody double reduction) throws CustomException{
        return customerService.getProductBySaleEOrA(reduction);
    }

    @PostMapping("/addItemInOrder")
    public void addItemInOrder(@RequestBody ItemInOrder item) throws CustomException{
        customerService.addItemInOrder(item);
    }


}
