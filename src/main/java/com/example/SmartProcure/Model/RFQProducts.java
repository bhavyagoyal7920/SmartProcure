package com.example.SmartProcure.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "rfq_products")
public class RFQProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String quantityRequired;
    private String note;
    @ManyToOne
    @JoinColumn(name = "rfq_id", nullable = false)
    @JsonBackReference
    private RequestForQuotation requestForQuotation;

    public RFQProducts() {
    }

    public RFQProducts(Long productId, String quantityRequired, String note, RequestForQuotation requestForQuotation) {
        this.productId = productId;
        this.quantityRequired = quantityRequired;
        this.note = note;
        this.requestForQuotation = requestForQuotation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(String quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public RequestForQuotation getRequestForQuotation() {
        return requestForQuotation;
    }

    public void setRequestForQuotation(RequestForQuotation requestForQuotation) {
        this.requestForQuotation = requestForQuotation;
    }
}
