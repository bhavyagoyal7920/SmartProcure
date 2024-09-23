package com.example.SmartProcure.Model;

import java.util.List;

public class AddProductResponse {
    public List<String> existingProduct;
    public List<Product> newProduct;

    public AddProductResponse(){}

    public AddProductResponse(List<String> existingProduct, List<Product> newProduct){
        this.existingProduct = existingProduct;
        for(Product product: newProduct){
            this.newProduct = newProduct;
        }
    }

    public List<String> getExistingProduct() {
        return existingProduct;
    }

    public void setExistingProduct(List<String> existingProduct) {
        this.existingProduct = existingProduct;
    }

    public List<Product> getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(List<Product> newProduct) {
        this.newProduct = newProduct;
    }
}
