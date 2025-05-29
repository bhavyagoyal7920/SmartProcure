package com.example.SmartProcure.Model;

import java.util.List;

public class VendorDetailsResponse {

    private Vendor vendorDetails;
    private List<Long> existingProductIds;

    // Getters and Setters

    public Vendor getVendorDetails() {
        return vendorDetails;
    }

    public void setVendorDetails(Vendor vendorDetails) {
        this.vendorDetails = vendorDetails;
    }

    public List<Long> getExistingProductIds() {
        return existingProductIds;
    }

    public void setExistingProductIds(List<Long> existingProductIds) {
        this.existingProductIds = existingProductIds;
    }
}
