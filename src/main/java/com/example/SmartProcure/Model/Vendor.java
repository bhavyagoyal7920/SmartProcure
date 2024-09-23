package com.example.SmartProcure.Model;

import com.example.SmartProcure.Model.Product;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
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
    @Column(name = "ContactNo")
    private Long contactNo;
    @Column(name = "EmailId")
    private String emailId;
    @Column(name = "POC")
    private String personOfContact;
    @Column(name = "POCcontactNo")
    private Long pocContactNo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(
            name = "vendorproductmap",
            joinColumns = @JoinColumn(name = "vendor_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    Set<Product> products;


    public Vendor(){
    }

    public Vendor(String name, String address, Long contactNo, String emailId, String personOfContact, Long pocContactNo) {
        this.name=name;
        this.address = address;
        this.contactNo = contactNo;
        this.emailId = emailId;
        this.personOfContact = personOfContact;
        this.pocContactNo = pocContactNo;
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        if(this.products == null){
            this.products = new HashSet<>();
        }
        this.products.addAll(products);
    }


}
