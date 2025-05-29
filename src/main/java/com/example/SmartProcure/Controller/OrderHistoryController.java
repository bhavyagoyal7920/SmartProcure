package com.example.SmartProcure.Controller;

import com.example.SmartProcure.DTO.OrderHistoryDTO;
import com.example.SmartProcure.Model.OrderHistory;
import com.example.SmartProcure.Service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/orderHistory")
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;
    @Autowired
    VendorController vendorController;

    @Autowired
    ProductController productController;

    public OrderHistoryController(OrderHistoryService orderHistoryService){
        this.orderHistoryService = orderHistoryService;
    }


    //to get orderHistory by vendor Id
    // http://localhost:8080/api/orderHistory/vendor/{vendorId}
    @GetMapping("/vendor/{vendorId}")
    public List<OrderHistory>  getOrderHistoryByVendor(@PathVariable Long vendorId){
        return orderHistoryService.getOrderHistoryByVendor(vendorId);
    }

    //to get orderHistory by product Id
    // http://localhost:8080/api/orderHistory/product/{productId}
    @GetMapping("/product/{productId}")
    public List<OrderHistory>  getOrderHistoryByProduct(@PathVariable Long productId){
        return orderHistoryService.getOrderHistoryByProduct(productId);
    }

    //http://localhost:8080/api/orderHistory/add
    @PostMapping("/add")
    public OrderHistory addOrderHistory(@RequestBody OrderHistoryDTO orderHistoryDTO){
        return  orderHistoryService.saveOrderHistory(orderHistoryDTO);
    }
}
