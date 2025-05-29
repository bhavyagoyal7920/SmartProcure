package com.example.SmartProcure.Repository;

import com.example.SmartProcure.Model.RFQProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RFQProductsRepository extends JpaRepository<RFQProducts, Long> {
    boolean existsByProductId(Long productId);
}
