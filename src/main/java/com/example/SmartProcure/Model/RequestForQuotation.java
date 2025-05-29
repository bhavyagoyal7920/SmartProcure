package com.example.SmartProcure.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "RFQ")
public class RequestForQuotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(mappedBy = "requestForQuotation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonManagedReference
    private List<RFQProducts> rfqProducts;


    public RequestForQuotation() {
    }

    public RequestForQuotation(Long id, String description, List<RFQProducts> rfqProducts) {
        this.id = id;
        this.description = description;
        this.rfqProducts = rfqProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RFQProducts> getRfqProducts() {
        return rfqProducts;
    }

    public void setRfqProducts(List<RFQProducts> rfqProducts) {
        this.rfqProducts = rfqProducts;
    }
}
