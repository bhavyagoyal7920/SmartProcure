package com.example.SmartProcure.DTO;

public class CartDTO {

    private long vendorId;
    private long productId;
    private String quantityRequired;
    private String note;

    public CartDTO() {
    }

    public CartDTO(long vendorId, long productId, String quantityRequired, String note) {
        this.vendorId = vendorId;
        this.productId = productId;
        this.quantityRequired = quantityRequired;
        this.note = note;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
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
