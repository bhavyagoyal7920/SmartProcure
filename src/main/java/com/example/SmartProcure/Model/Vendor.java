package com.example.SmartProcure.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "Name")
    private String name;
    @Column(name = "Address")
    private String address;

    @Column(name = "City")
    private String city;

    @Column(name = "Pincode")
    private String pincode;

    @Column(name = "GSTNo")
    private String gstNo;
    @Column(name = "ContactNo")
    private Long contactNo;
    @Column(name = "EmailId")
    private String emailId;
    @Column(name = "POC1")
    private String personOfContact1;
    @Column(name = "POC1contactNo")
    private Long poc1ContactNo;
    @Column(name = "POC1emailId")
    private  String poc1EmailId;
    @Column(name = "DesignationOfPerson1")
    private String designationOfPerson1;
    @Column(name = "POC2")
    private String personOfContact2;
    @Column(name = "POC2contactNo")
    private Long poc2ContactNo;

    @Column(name = "POC2emailId")
    private  String poc2EmailId;
    @Column(name = "DesignationOfPerson2")
    private String designationOfPerson2;
    @Column(name = "POC3")
    private String personOfContact3;
    @Column(name = "POC3contactNo")
    private Long poc3ContactNo;
    @Column(name = "POC3emailId")
    private  String poc3EmailId;
    @Column(name = "DesignationOfPerson3")
    private String designationOfPerson3;

    @Column(name = "LastPurchasedOn")
    private Date lastPurchasedOn = null;

    @Column(name = "Notes")
    private String notes;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(
            name = "vendorproductmap",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    public Vendor(){
    }

    public Vendor(String name, String gstNo, String address, String city, String pincode, Long contactNo, String emailId, String personOfContact1, Long poc1ContactNo, String poc1EmailId,  String designationOfPerson1,String personOfContact2, Long poc2ContactNo, String poc2EmailId, String designationOfPerson2,String personOfContact3, Long poc3ContactNo, String poc3EmailId, String designationOfPerson3, String notes) {
        this.name = name;
        this.gstNo = gstNo;
        this.address = address;
        this.city = city;
        this.pincode = pincode;
        this.contactNo = contactNo;
        this.emailId = emailId;
        this.personOfContact1 = personOfContact1;
        this.poc1ContactNo = poc1ContactNo;
        this.poc1EmailId = poc1EmailId;
        this.designationOfPerson1 = designationOfPerson1;
        this.personOfContact2 = personOfContact2;
        this.poc2ContactNo = poc2ContactNo;
        this.poc2EmailId = poc2EmailId;
        this.designationOfPerson2 = designationOfPerson2;
        this.personOfContact3 = personOfContact3;
        this.poc3ContactNo = poc3ContactNo;
        this.poc3EmailId = poc3EmailId;
        this.designationOfPerson3 = designationOfPerson3;
        this.notes = notes;
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

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
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

    public void setPoc3ContactNo(Long poc3ContactNo) {
        this.poc3ContactNo = poc3ContactNo;
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



    public Date getLastPurchasedOn() {
        return lastPurchasedOn;
    }

    public void setLastPurchasedOn(Date lastPurchasedOn) {
        this.lastPurchasedOn = lastPurchasedOn;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        if(this.products == null){
            this.products = new HashSet<>();
        }
        System.out.println("all products:"+products);
        this.products.addAll(products);
    }



}
