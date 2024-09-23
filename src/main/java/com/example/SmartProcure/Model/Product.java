package com.example.SmartProcure.Model;

import jakarta.persistence.*;

import java.util.Set;

import static org.hibernate.internal.util.collections.ArrayHelper.forEach;

@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Name")
    private String techName;
    @Column(name = "Alias")
    private String alias;

    @Column(name = "Brand")
    private String brand;
    @Column(name = "Category")
    private String category;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
    @JoinTable(
            name = "vendorproductmap",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "vendor_id")
    )
    Set<Vendor> vendors;

    public Product(){}

    public Product(String techName, String alias, String brand, String category) {
        this.techName = techName;
        this.alias = alias;
        this.brand = brand;
        this.category = category;
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

    public Set<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(Set<Vendor> vendors) {
        this.vendors.addAll(vendors);
    }


}
