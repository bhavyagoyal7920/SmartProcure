package com.example.SmartProcure.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name ="Vendor_Id")
    private Long vendorId;
    @Column(name = "Vendor_Name")
    private String vendorName;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CartProductData> products;

    public Cart() {
    }

    public Cart(Long vendorId, String vendorName, List<CartProductData> products) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.products = products;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public List<CartProductData> getProducts() {
        return products;
    }

    public void setProducts(List<CartProductData> products) {
        this.products = products;
    }
}
