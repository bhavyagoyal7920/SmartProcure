package com.example.SmartProcure.DTO;

import java.util.Date;

public class OrderHistoryDTO {

    private Long vendorId;
    private Long productId;
    private int quantity;
    private Date purchaseDate;
    private double totalAmount;

    public OrderHistoryDTO() {
    }

    public OrderHistoryDTO(Long vendorId, Long productId, int quantity, Date purchaseDate, double totalAmount) {
        this.vendorId = vendorId;
        this.productId = productId;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
