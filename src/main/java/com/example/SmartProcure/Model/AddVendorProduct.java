package com.example.SmartProcure.Model;

public class AddVendorProduct {

    public Vendor vendor;
    public Product product;

    public AddVendorProduct(){}

    public  AddVendorProduct(Vendor vendor, Product product){
        this.vendor = vendor;
        this.product = product;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
