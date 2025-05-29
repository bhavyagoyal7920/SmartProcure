package com.example.SmartProcure.Repository;
import java.util.List;

import com.example.SmartProcure.Model.OrderHistory;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findByVendorId(Long vendorId);
    List<OrderHistory> findByProductId(Long productId);

}
