package com.example.SmartProcure.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "Make")
    private String make;
    @Column(name = "Category")
    private String category;

    @Column(name = "Description")
    private String description;

    @Column(name = "Notes")
    private String notes;

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private Set<Vendor> vendors = new HashSet<>();

    public Product(){}

    public Product(String techName, String alias, String make, String category, String description, String notes) {
        this.techName = techName;
        this.alias = alias;
        this.make = make;
        this.category = category;
        this.description = description;
        this.notes = notes;
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

    public void setMake(String make) {
        this.make = make;
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
}
