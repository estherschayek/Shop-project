package com.example.shop_project.repositories;

import com.example.shop_project.models.Category;
import com.example.shop_project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ProductRepo extends JpaRepository<Product,Long> {


    public Product findProductByNameContainingIgnoreCase(String name);

    public Set<Product> findProductsByPrice(double price);

    public Set<Product> findProductsByCategory(Category c);

    @Query("select p from Product p join p.sale s where s.reduction>=?1")
    public Set<Product> findProductsBySaleEqualsOrAfter(double reduction);


}
