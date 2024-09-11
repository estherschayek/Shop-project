package com.example.shop_project.repositories;

import com.example.shop_project.models.Order;
import com.example.shop_project.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepo extends JpaRepository<Sale, Long> {



    @Query("select s from Sale s join s.product p where s.id=?1 and p.id=?2")
    public boolean existsSalesByIdAndProductId(Long id, Long productIdId);

    //Customer
    @Query("select s from Sale s join s.product p where p.id=?1 ")
    public Sale getSaleForProductId(Long id);

    @Query("select s from Order o join o.itemsInOrder i join i.product p join p.sale s where p.id=?1 and i.id=?2 and i.quantity >= s.quantitySaled")
    public Sale getSaleForItemInOrder(Long pId, Long itemId);
    //Customer

    @Query("select  count(i)>0 from Order o join o.itemsInOrder i  join i.product p join p.sale s where o.id=?1 and i.quantity >= s.quantitySaled ")
    public boolean isOrderWithSales(Long id);


}
