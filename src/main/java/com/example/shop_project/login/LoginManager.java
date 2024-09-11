package com.example.shop_project.login;

import com.example.shop_project.exception.CustomException;
import com.example.shop_project.models.CustomerType;
import com.example.shop_project.services.AdminService;
import com.example.shop_project.services.ClientService;
import com.example.shop_project.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class LoginManager {
    @Autowired
    AdminService adminService;

    @Autowired
    CustomerService customerService;

    private LoginManager() {

    }

    public ClientService login(String email , String password, CustomerType customerType) throws  CustomException {
        boolean boolLogin = false;
        ClientService clientService = null;
        boolLogin = switch (customerType) {
            case Administrator -> {
                clientService = adminService;
                yield clientService.login(email, password);
            }
            case Customer -> {
                clientService = customerService;
                yield clientService.login(email, password);
            }
        };

        if(!boolLogin)
            return null;
        else
            return clientService;
    }
}
