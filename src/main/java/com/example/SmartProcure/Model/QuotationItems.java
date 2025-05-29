package com.example.SmartProcure.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "quotation_items")
public class QuotationItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quotation_form_id", nullable = false)
    @JsonIgnore
    private QuotationForm quotationForm;

    private Long productId;
    private String quantityRequired;
    private String note;

    public QuotationItems() {
    }

    public QuotationItems(Long id, QuotationForm quotationForm, Long productId, String quantityRequired, String note) {
        this.id = id;
        this.quotationForm = quotationForm;
        this.productId = productId;
        this.quantityRequired = quantityRequired;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuotationForm getQuotationForm() {
        return quotationForm;
    }

    public void setQuotationForm(QuotationForm quotationForm) {
        this.quotationForm = quotationForm;
    }

    public Long getProductId() {
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

}
