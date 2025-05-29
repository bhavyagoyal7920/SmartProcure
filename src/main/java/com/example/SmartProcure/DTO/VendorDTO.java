package com.example.SmartProcure.DTO;

import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendorDTO {
    private long id;
    private String name;
    private String gstNo;
    private String address;
    private String city;
    private String pincode;
    private Long contactNo;
    private String emailId;
    private String personOfContact1;
    private Long poc1ContactNo;
    private String poc1EmailId;
    private String designationOfPerson1;
    private String personOfContact2;
    private Long poc2ContactNo;
    private String poc2EmailId;
    private String designationOfPerson2;
    private String personOfContact3;
    private Long poc3ContactNo;
    private String poc3EmailId;
    private String designationOfPerson3;
    private String notes;
    private Date lastPurchasedOn;

    private List<ProductDTO> products = new ArrayList<>();

    public VendorDTO(){}

    public VendorDTO(Vendor vendor){
        this.id = vendor.getId();
        this.name = vendor.getName();
        this.gstNo = vendor.getGstNo();
        this.address = vendor.getAddress();
        this.city = vendor.getCity();
        this.pincode = vendor.getPincode();
        this.contactNo = vendor.getContactNo();
        this.emailId = vendor.getEmailId();
        this.personOfContact1 = vendor.getPersonOfContact1();
        this.poc1ContactNo = vendor.getPoc1ContactNo();
        this.poc1EmailId = vendor.getPoc1EmailId();
        this.designationOfPerson1 = vendor.getDesignationOfPerson1();
        this.personOfContact2 = vendor.getPersonOfContact2();
        this.poc2ContactNo = vendor.getPoc2ContactNo();
        this.poc2EmailId = vendor.getPoc2EmailId();
        this.designationOfPerson2 = vendor.getDesignationOfPerson2();
        this.personOfContact3 = vendor.getPersonOfContact3();
        this.poc3ContactNo = vendor.getPoc3ContactNo();
        this.poc3EmailId = vendor.getPoc3EmailId();
        this.designationOfPerson3 = vendor.getDesignationOfPerson3();
        this.notes = vendor.getNotes();
        this.lastPurchasedOn = vendor.getLastPurchasedOn();
        for(Product product: vendor.getProducts()){
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setTechName(product.getTechName());
            productDTO.setAlias(product.getAlias());
            productDTO.setMake(product.getMake());
            productDTO.setCategory(product.getCategory());
            productDTO.setDescription(product.getDescription());
            productDTO.setNotes(product.getNotes());
            products.add(productDTO);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public String getPersonOfContact1() {
        return personOfContact1;
    }

    public void setPersonOfContact1(String personOfContact1) {
        this.personOfContact1 = personOfContact1;
    }

    public Long getPoc1ContactNo() {
        return poc1ContactNo;
    }

    public void setPoc1ContactNo(Long poc1ContactNo) {
        this.poc1ContactNo = poc1ContactNo;
    }

    public String getPersonOfContact2() {
        return personOfContact2;
    }

    public void setPersonOfContact2(String personOfContact2) {
        this.personOfContact2 = personOfContact2;
    }

    public Long getPoc2ContactNo() {
        return poc2ContactNo;
    }

    public void setPoc2ContactNo(Long poc2ContactNo) {
        this.poc2ContactNo = poc2ContactNo;
    }
    public String getPersonOfContact3() {
        return personOfContact3;
    }

    public void setPersonOfContact3(String personOfContact3) {
        this.personOfContact3 = personOfContact3;
    }

    public Long getPoc3ContactNo() {
        return poc3ContactNo;
    }

    public void setPoc3ContactNo(Long pocContactNo) {
        this.poc3ContactNo = pocContactNo;
    }

    public String getDesignationOfPerson1() {
        return designationOfPerson1;
    }

    public void setDesignationOfPerson1(String designationOfPerson1) {
        this.designationOfPerson1 = designationOfPerson1;
    }

    public String getDesignationOfPerson2() {
        return designationOfPerson2;
    }

    public void setDesignationOfPerson2(String designationOfPerson2) {
        this.designationOfPerson2 = designationOfPerson2;
    }

    public String getDesignationOfPerson3() {
        return designationOfPerson3;
    }

    public void setDesignationOfPerson3(String designationOfPerson3) {
        this.designationOfPerson3 = designationOfPerson3;
    }

    public String getPoc1EmailId() {
        return poc1EmailId;
    }

    public void setPoc1EmailId(String poc1EmailId) {
        this.poc1EmailId = poc1EmailId;
    }

    public String getPoc2EmailId() {
        return poc2EmailId;
    }

    public void setPoc2EmailId(String poc2EmailId) {
        this.poc2EmailId = poc2EmailId;
    }

    public String getPoc3EmailId() {
        return poc3EmailId;
    }

    public void setPoc3EmailId(String poc3EmailId) {
        this.poc3EmailId = poc3EmailId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public Date getLastPurchasedOn() {
        return lastPurchasedOn;
    }

    public void setLastPurchasedOn(Date lastPurchasedOn) {
        this.lastPurchasedOn = lastPurchasedOn;
    }
}
