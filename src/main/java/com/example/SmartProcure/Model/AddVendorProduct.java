package com.example.SmartProcure.Model;

import java.util.List;

public class AddVendorProduct {

    public Vendor vendor;
    public List<String> existingProduct;
    public List<Product> newProduct;


    public AddVendorProduct(){}

    public  AddVendorProduct(Vendor vendor, List<String> existingProduct, List<Product> newProduct){
        this.vendor = vendor;
        this.newProduct = newProduct;
        this.existingProduct = existingProduct;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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
