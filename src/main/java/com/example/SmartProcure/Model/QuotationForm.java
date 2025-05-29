package com.example.SmartProcure.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "quotation_forms")
public class QuotationForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long vendorId;
    private String description;
    private LocalDateTime createdAt;
    private String submittedByName;
    private String submittedByPhone;
    private LocalDateTime submittedAt;
    private String documentPath;

    @OneToMany(mappedBy = "quotationForm", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private List<QuotationItems> items;

    public QuotationForm() {
    }

    public QuotationForm(Long id, Long vendorId, String description, LocalDateTime createdAt, String submittedByName, String submittedByPhone, LocalDateTime submittedAt, String documentPath, List<QuotationItems> items) {
        this.id = id;
        this.vendorId = vendorId;
        this.description = description;
        this.createdAt = createdAt;
        this.submittedByName = submittedByName;
        this.submittedByPhone = submittedByPhone;
        this.submittedAt = submittedAt;
        this.documentPath = documentPath;
        this.items = items;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getSubmittedByName() {
        return submittedByName;
    }

    public void setSubmittedByName(String submittedByName) {
        this.submittedByName = submittedByName;
    }

    public String getSubmittedByPhone() {
        return submittedByPhone;
    }

    public void setSubmittedByPhone(String submittedByPhone) {
        this.submittedByPhone = submittedByPhone;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public List<QuotationItems> getItems() {
        return items;
    }

    public void setItems(List<QuotationItems> items) {
        this.items = items;
    }
}
