package com.example.SmartProcure.DTO;

public class SearchResultDTO {
    private long vendorId;
    private String vendorName;
    private long productId;
    private String productName;
    private String alias;
    private String make;
    private String category;
    public SearchResultDTO(Long vendorId, String vendorName, Long productId, String productName, String alias, String make, String category) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.productId = productId;
        this.productName = productName;
        this.alias = alias;
        this.make = make;
        this.category = category;
    }


    // Getters and setters


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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
}

