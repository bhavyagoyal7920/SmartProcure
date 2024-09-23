package com.example.SmartProcure.Model;

import com.example.SmartProcure.Model.Vendor;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.Date;

@Entity
public class VendorDetails {

    @Id
    @OneToOne
    private Vendor vendorId;
    private float rating;
    private int frequencyOfPurchase;
    private Date lastPurchasedOn;
    private double lastSellingPrice;

    public Vendor getVendorId() {
        return vendorId;
    }

    public void setVendorId(Vendor vendorId) {
        this.vendorId = vendorId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getFrequencyOfPurchase() {
        return frequencyOfPurchase;
    }

    public void setFrequencyOfPurchase(int frequencyOfPurchase) {
        this.frequencyOfPurchase = frequencyOfPurchase;
    }

    public Date getLastPurchasedOn() {
        return lastPurchasedOn;
    }

    public void setLastPurchasedOn(Date lastPurchasedOn) {
        this.lastPurchasedOn = lastPurchasedOn;
    }

    public double getLastSellingPrice() {
        return lastSellingPrice;
    }

    public void setLastSellingPrice(double lastSellingPrice) {
        this.lastSellingPrice = lastSellingPrice;
    }
}
