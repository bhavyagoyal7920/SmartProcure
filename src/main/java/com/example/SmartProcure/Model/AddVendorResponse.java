package com.example.SmartProcure.Model;

import java.util.List;


public class AddVendorResponse {
    public List<String> existingVendor;
    public List<Vendor> newVendors;
    public AddVendorResponse() {}

    public AddVendorResponse(List<String> existingVendor, List<Vendor> newVendors) {
        this.existingVendor = existingVendor;

            this.newVendors = newVendors;

    }

    public List<String> getExistingVendor() {
        return existingVendor;
    }

    public void setExistingVendor(List<String> existing) {
        this.existingVendor = existing;
    }

    public List<Vendor> getNewVendors() {
        return newVendors;
    }

    public void setNewVendors(List<Vendor> newVendors) {
        this.newVendors = newVendors;
    }
}
