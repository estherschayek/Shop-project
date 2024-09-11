package com.example.shop_project.controlers;

import com.example.shop_project.exception.CustomException;
import com.example.shop_project.jwt.AuthenticationRequest;
import com.example.shop_project.jwt.AuthenticationResponse;
import com.example.shop_project.jwt.JwtUtils;
import com.example.shop_project.login.LoginRequest;
import com.example.shop_project.models.*;
import com.example.shop_project.repositories.SaleRepo;
import com.example.shop_project.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController extends ClientController{



    @Autowired
    private JwtUtils jwtUtils;

    private AdminService adminService;



    public AdminController() {

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws CustomException {
        adminService = (AdminService) loginManager.login(authenticationRequest.getEmail(), authenticationRequest.getPassword(), CustomerType.Administrator);
        if(adminService != null) {
          // String token = jwtUtils.generateToken(authenticationRequest, CustomerType.Administrator);
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/addCustomer")
    public void addCustomer(@RequestBody Customer c) throws CustomException{
        adminService.addCustomer(c);
    }

    @PutMapping ("/updateCustomer")
    public void updateCustomer(@RequestBody Customer c) throws CustomException{
        adminService.updateCustomer(c);
    }

    @DeleteMapping  ("/deleteCustomer/{id}")
    public void deleteCustomer(@PathVariable Long id) throws CustomException{
        adminService.deleteCustomer(id);
    }

    @GetMapping ("/allCustomer")
    public List<Customer> getAllCust()throws CustomException{
        return adminService.getAllCustomers();
    }

    @GetMapping("/getOneCus/{id}")
    public Customer getOneCustomer(@PathVariable Long id) throws CustomException{
        return adminService.getOneCustomer(id);
    }

    @GetMapping ("/getCustQuantGreaterThan")
    public List<Object[]> getCustomQuantMoreThan(@RequestBody int quantity) throws CustomException{
        return adminService.getCustomerQuantityGreaterThan(quantity);
    }

    @GetMapping ("/getCusOrders")
    public Map<Long,List<Order>> getAllOrdersCust() throws CustomException{
        return adminService.getAllOrdersOfAllCustomers();
    }

    @GetMapping("/getCustomerOrderById/{id}")
     public List<Order>  getCustomerOrderById(@PathVariable Long id) throws CustomException{
        return adminService.getCustomerOrderById(id);
    }
    @GetMapping("/getSpecificOrderByCustomIdAndDate/{id}")
    public Order getSpecificOrderByCustomIdAndDate (@PathVariable Long id, @RequestBody Date date) throws CustomException{
        return adminService.getSpecificOrderByCustomIdAndDate(id,date);
    }

    @GetMapping("/getOrdersByCustomerIdAndFinalPrice/{id}")
    public List<Order> getOrdersByCustomerIdAndFinalPrice(@PathVariable Long id, @RequestBody double price) throws CustomException{
        return adminService.getOrdersByCustomerIdAndFinalPrice(id,price);
    }

    @GetMapping("/getOrdersByOrderDateBetween")
    public List<Order> getOrdersByOrderDateBetween(@RequestBody Date d1,Date d2) throws CustomException{
        return adminService.getOrdersByOrderDateBetween(d1,d2);
    }

    @PostMapping("/addSale")
    public void addSale(@RequestBody Sale s) throws CustomException{
        adminService.addSale(s);
    }

    @PutMapping ("/updateSale")
    public void updateSale(@RequestBody Sale s) throws CustomException{
        adminService.updateSale(s);
    }

    @DeleteMapping  ("/deleteSale")
    public void deleteSale(@RequestBody Sale sale) throws CustomException{
        adminService.deleteSale(sale);
    }

    @GetMapping ("/allSale")
    public List<Sale> getAllSale()throws CustomException{
        return adminService.getAllSale();
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody Product p) throws CustomException{
        adminService.addProduct(p);
    }

    @PutMapping ("/updateProduct")
    public void updateProduct(@RequestBody Product p) throws CustomException{
        adminService.updateProduct(p);
    }

    @DeleteMapping  ("/deleteProduct")
    public void deleteProduct(@RequestBody Product p) throws CustomException{
        adminService.deleteProduct(p);
    }

    @GetMapping ("/allProducts")
    public List<Product> getAllProduct()throws CustomException{
        return adminService.getProducts();
    }



}
