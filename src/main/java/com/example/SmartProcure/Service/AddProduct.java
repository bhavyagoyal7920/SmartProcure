package com.example.SmartProcure.Service;

import com.example.SmartProcure.DTO.ProductDTO;
import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import com.example.SmartProcure.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public ResponseEntity<String> deleteProduct(Long productId) {
        // Find the product by ID
        try{
            Product product = productRepository.getReferenceById(productId);

            // Remove the product from each associated vendor's product set
            for (Vendor vendor : product.getVendors()) {
                vendor.getProducts().remove(product);
            }

            // Clear the vendors set from the product to prevent any orphan removal
            product.getVendors().clear();
            productRepository.save(product); // Ensure relationships are updated

            // Now delete the product
            productRepository.deleteById(productId);
            return new ResponseEntity<>("Product deleted Successfully", HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
