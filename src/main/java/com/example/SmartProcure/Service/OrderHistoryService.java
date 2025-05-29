package com.example.SmartProcure.Service;

import com.example.SmartProcure.DTO.OrderHistoryDTO;
import com.example.SmartProcure.DTO.VendorDTO;
import com.example.SmartProcure.Model.OrderHistory;
import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import com.example.SmartProcure.Repository.OrderHistoryRepository;
import com.example.SmartProcure.Repository.ProductRepository;
import com.example.SmartProcure.Repository.VendorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderHistoryService {
    private final OrderHistoryRepository orderHistoryRepository;
    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;

    public OrderHistoryService(OrderHistoryRepository orderHistoryRepository,
                               VendorRepository vendorRepository,
                               ProductRepository productRepository){
        this.orderHistoryRepository = orderHistoryRepository;
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
    }

    public List<OrderHistory> getOrderHistoryByVendor(Long vendorId){
        return orderHistoryRepository.findByVendorId(vendorId);
    }

    public List<OrderHistory> getOrderHistoryByProduct(Long productId){
        return  orderHistoryRepository.findByProductId(productId);
    }

    public OrderHistory saveOrderHistory(OrderHistoryDTO orderHistoryDTO){
        Vendor vendor = vendorRepository.findById(orderHistoryDTO.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        Product product = productRepository.findById(orderHistoryDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));;

        OrderHistory orderHistory = new OrderHistory(vendor,
                                                    product,
                                                    orderHistoryDTO.getQuantity(),
                                                    orderHistoryDTO.getPurchaseDate(),
                                                    orderHistoryDTO.getTotalAmount());
        return  orderHistoryRepository.save(orderHistory);
    }
}
