package com.example.shop_project.repositories;

import com.example.shop_project.models.ItemInOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemInOrderRepo extends JpaRepository<ItemInOrder,Long> {


}
