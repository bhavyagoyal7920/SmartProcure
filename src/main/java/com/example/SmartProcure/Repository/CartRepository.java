package com.example.SmartProcure.Repository;

import com.example.SmartProcure.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
//    Cart findByVendorId(Long vendorId);
//    List<Cart> findAllVendorsById(Long vendorId);
}
