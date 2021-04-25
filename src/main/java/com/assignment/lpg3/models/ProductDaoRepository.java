package com.assignment.lpg3.models;

import java.util.List;

import com.assignment.lpg3.models.data.ProductDao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDaoRepository extends JpaRepository<ProductDao, Integer> {
    
    List<ProductDao> findAll();

}
 