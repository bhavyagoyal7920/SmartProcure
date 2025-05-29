package com.example.SmartProcure.Service;

import com.example.SmartProcure.Model.RFQProducts;
import com.example.SmartProcure.Repository.RFQProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RFQService {

    @Autowired
    RFQProductsRepository rfqProductsRepository;

    public boolean checkProductInRFQ(Long productId){
        return rfqProductsRepository.existsByProductId(productId);
    }
}
