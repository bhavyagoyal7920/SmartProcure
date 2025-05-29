package com.example.SmartProcure.DTO;

import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;


public class ProductDTO {
    private Long id;
    private String techName;
    private String alias;

    private String make;
    private String category;
    private String description;
    private String notes;
    private List<VendorDTO> vendors = new ArrayList<>();

    public ProductDTO(){}

    public ProductDTO(Product product){
        this.id = product.getId();
        this.techName = product.getTechName();
        this.alias = product.getAlias();
        this.make = product.getMake();
        this.category = product.getCategory();
        this.description = product.getDescription();
        this.notes = product.getNotes();
        for(Vendor vendor: product.getVendors()){
            VendorDTO vendorDTO = new VendorDTO();
            vendorDTO.setId(vendor.getId());
            vendorDTO.setName(vendor.getName());
            vendorDTO.setGstNo(vendor.getGstNo());
            vendorDTO.setAddress(vendor.getAddress());
            vendorDTO.setCity(vendor.getCity());
            vendorDTO.setPincode(vendor.getPincode());
            vendorDTO.setContactNo(vendor.getContactNo());
            vendorDTO.setEmailId(vendor.getEmailId());
            vendorDTO.setPersonOfContact1(vendor.getPersonOfContact1());
            vendorDTO.setPoc1ContactNo(vendor.getPoc1ContactNo());
            vendorDTO.setPoc1EmailId(vendor.getPoc1EmailId());
            vendorDTO.setDesignationOfPerson1(vendor.getDesignationOfPerson1());
            vendorDTO.setPersonOfContact2(vendor.getPersonOfContact2());
            vendorDTO.setPoc2ContactNo(vendor.getPoc2ContactNo());
            vendorDTO.setPoc2EmailId(vendor.getPoc2EmailId());
            vendorDTO.setDesignationOfPerson2(vendor.getDesignationOfPerson2());
            vendorDTO.setPersonOfContact3(vendor.getPersonOfContact3());
            vendorDTO.setPoc3ContactNo(vendor.getPoc3ContactNo());
            vendorDTO.setPoc3EmailId(vendor.getPoc3EmailId());
            vendorDTO.setDesignationOfPerson3(vendor.getDesignationOfPerson3());
            vendorDTO.setNotes(vendor.getNotes());
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

    public String getMake() {
        return make;
    }

    public void setMake(String brand) {
        this.make = make;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<VendorDTO> getVendors() {
        return vendors;
    }

    public void setVendors(List<VendorDTO> vendors) {
        this.vendors = vendors;
    }
}
