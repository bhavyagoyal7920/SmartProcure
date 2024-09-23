package com.example.SmartProcure.Repository;

import com.example.SmartProcure.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
