package com.example.SmartProcure.DTO;

import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import jakarta.persistence.Column;

import java.util.ArrayList;
import java.util.List;

public class VendorDTO {
    private long id;

    private String name;
    private String address;
    private Long contactNo;
    private String emailId;
    private String personOfContact;
    private Long pocContactNo;
    private List<ProductDTO> products = new ArrayList<>();

    public VendorDTO(){}

    public VendorDTO(Vendor vendor){
        this.id = vendor.getId();
        this.name = vendor.getName();
        this.address = vendor.getAddress();
        this.contactNo = vendor.getContactNo();
        this.emailId = vendor.getEmailId();
        this.personOfContact = vendor.getPersonOfContact();
        this.pocContactNo = vendor.getPocContactNo();
        for(Product product: vendor.getProducts()){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setTechName(product.getTechName());
            productDTO.setAlias(product.getAlias());
            productDTO.setBrand(product.getBrand());
            productDTO.setCategory(product.getCategory());
            products.add(productDTO);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getContactNo() {
        return contactNo;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPersonOfContact() {
        return personOfContact;
    }

    public void setPersonOfContact(String personOfContact) {
        this.personOfContact = personOfContact;
    }

    public Long getPocContactNo() {
        return pocContactNo;
    }

    public void setPocContactNo(Long pocContactNo) {
        this.pocContactNo = pocContactNo;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
