package com.assignment.lpg3.dao_management.repository;

import java.util.List;

import com.assignment.lpg3.dao_management.dao.ProductDao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDaoRepository extends JpaRepository<ProductDao, Integer> {
    
    List<ProductDao> findAll();

}
 