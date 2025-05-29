package com.example.SmartProcure.DTO;

import com.example.SmartProcure.Model.QuotationItems;

import java.util.Date;

public class QuotationItemDTO {

    private Long productId;
    private String quantityRequired;
    private String note;

    public QuotationItemDTO(QuotationItems item) {
        this.productId = item.getProductId();
        this.quantityRequired = item.getQuantityRequired();
        this.note = item.getNote();
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
