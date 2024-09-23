package com.example.SmartProcure.DTO;

import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import jakarta.persistence.Column;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {
    private Long id;
    private String techName;
    private String alias;

    private String brand;
    private String category;
    private List<VendorDTO> vendors = new ArrayList<>();

    public ProductDTO(){}

    public ProductDTO(Product product){
        this.id = product.getId();
        this.techName = product.getTechName();
        this.alias = product.getAlias();
        this.brand = product.getBrand();;
        this.category = product.getCategory();
        for(Vendor vendor: product.getVendors()){
            VendorDTO vendorDTO = new VendorDTO();
            vendorDTO.setId(vendor.getId());
            vendorDTO.setName(vendor.getName());
            vendorDTO.setAddress(vendor.getAddress());
            vendorDTO.setContactNo(vendor.getContactNo());
            vendorDTO.setEmailId(vendor.getEmailId());
            vendorDTO.setPersonOfContact(vendor.getPersonOfContact());
            vendorDTO.setPocContactNo(vendor.getPocContactNo());
            vendors.add(vendorDTO);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<VendorDTO> getVendors() {
        return vendors;
    }

    public void setVendors(List<VendorDTO> vendors) {
        this.vendors = vendors;
    }
}
