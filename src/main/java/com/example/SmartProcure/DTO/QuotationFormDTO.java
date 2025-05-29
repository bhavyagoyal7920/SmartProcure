package com.example.SmartProcure.DTO;

import com.example.SmartProcure.Model.QuotationForm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class QuotationFormDTO {
    private Long id;
    private Long vendorId;
    private String vendorName;
    private LocalDateTime createdAt;
    private String submittedByName;
    private String submittedByPhone;
    private List<QuotationItemDTO> items;

    public QuotationFormDTO(QuotationForm form) {
        this.id = form.getId();
        this.vendorId = form.getVendorId();
        this.createdAt = form.getCreatedAt();
        this.submittedByName = form.getSubmittedByName();
        this.submittedByPhone = form.getSubmittedByPhone();
        this.items = form.getItems().stream()
                .map(QuotationItemDTO::new)
                .collect(Collectors.toList());
    }

    public List<QuotationItemDTO> getItems() {
        return items;
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

    public void setItems(List<QuotationItemDTO> items) {
        this.items = items;
    }
}
