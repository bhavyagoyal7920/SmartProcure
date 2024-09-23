package com.example.SmartProcure.Service;

import com.example.SmartProcure.DTO.ProductDTO;
import com.example.SmartProcure.DTO.VendorDTO;
import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import com.example.SmartProcure.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddProduct {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : products){
            ProductDTO productDTO = new ProductDTO(product);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }
    public ProductDTO getProductById(Long id){
        Product product = productRepository.getReferenceById(id);
        ProductDTO productDTO = new ProductDTO(product);
        return productDTO;
    }
}
