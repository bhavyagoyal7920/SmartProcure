package com.example.SmartProcure.Controller;

import com.example.SmartProcure.DTO.VendorDTO;
import com.example.SmartProcure.Model.AddVendorResponse;
import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import com.example.SmartProcure.Repository.ProductRepository;
import com.example.SmartProcure.Repository.VendorRepository;
import com.example.SmartProcure.Service.VendorOnboarding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private VendorOnboarding vendorOnboarding;
    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/getVendors")
    public List<VendorDTO> getVendors() {
        return vendorOnboarding.getAllVendors();
    }

    @GetMapping("/getVendor/{id}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable(name = "id") Long id) {
        try {
            VendorDTO vendorDTO = vendorOnboarding.getVendorById(id);
            return new ResponseEntity<>(vendorDTO, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    //save a new vendor
    @PostMapping("/createVendor")
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
        try {
            Vendor newVendor = new Vendor(vendor.getName(), vendor.getAddress(), vendor.getContactNo(), vendor.getEmailId(), vendor.getPersonOfContact(), vendor.getPocContactNo());
            Vendor tempVendor = vendorRepository.save(newVendor);
            return new ResponseEntity<>(tempVendor, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //save a new vendor to an existing product
    @PostMapping("/createVendorForProduct/{prodId}")
    public ResponseEntity<String> createVendorForProduct(@RequestBody Vendor vendor, @PathVariable(name = "prodId") String prodId) {

        try {
            Vendor newVendor = new Vendor(vendor.getName(), vendor.getAddress(), vendor.getContactNo(), vendor.getEmailId(), vendor.getPersonOfContact(), vendor.getPocContactNo());
            vendorRepository.save(newVendor);


            Product product = this.productRepository.getReferenceById(Long.valueOf(prodId));

            Set<Vendor> vendors = new HashSet<>();
            vendors.add(newVendor);

            product.setVendors(vendors);
            productRepository.save(product);

            return new ResponseEntity<>("Vendor added to the product successfully!", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //add existing vendor or  new vendor to an existing product
    @PostMapping("/addVendorsToProduct/{prodId}")
    public ResponseEntity<String> addVendorsToProduct(@PathVariable(name = "prodId") String prodId, @RequestBody AddVendorResponse obj){
        int count =0;
        if (obj.existingVendor != null) {
            System.out.println("Existing Vendors: " + obj.existingVendor);
            Product product = this.productRepository.getReferenceById(Long.valueOf(prodId));
            Set<Vendor> vendors = new HashSet<>();
            for(String vendId: obj.existingVendor){
                Vendor tempVendor = this.vendorRepository.getReferenceById(Long.valueOf(vendId));
                vendors.add(tempVendor);
            }
            product.setVendors(vendors);
            productRepository.save(product);
        } else {
            System.out.println("No existing vendors provided.");
        }

        if (obj.newVendors != null) {
            System.out.println("New Vendor: " + obj.newVendors);

            for(Vendor vendor: obj.getNewVendors()){
                ResponseEntity<String> res= createVendorForProduct(vendor, prodId);
                if(res.getStatusCode() == HttpStatus.OK){
                    count++;
                }
            }

        } else {
            System.out.println("No new vendor provided.");
        }
        if(obj.existingVendor == null && obj.newVendors == null){
            return new ResponseEntity<>("Vendor details should exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Product added successfully. (No. of new vendors added: "+count+")", HttpStatus.OK);
    }

    //update details of a an existing Vendor
    @PutMapping("/updateVendorDetails/{vendId}")
    public ResponseEntity<String> updateVendorDetails(@RequestBody Vendor vendor, @PathVariable(name = "vendId") String vendId){
        try{
            Vendor existingVendor = this.vendorRepository.getReferenceById(Long.valueOf(vendId));
            existingVendor.setName(vendor.getName());
            existingVendor.setAddress(vendor.getAddress());
            existingVendor.setContactNo(vendor.getContactNo());
            existingVendor.setEmailId(vendor.getEmailId());
            existingVendor.setPersonOfContact(vendor.getPersonOfContact());
            existingVendor.setPocContactNo(vendor.getPocContactNo());
            existingVendor.setRating(vendor.getRating());
            existingVendor.setFrequencyOfPurchase(vendor.getFrequencyOfPurchase());
            existingVendor.setLastPurchasedOn(vendor.getLastPurchasedOn());
            vendorRepository.save(existingVendor);
            return  new ResponseEntity<>("Updated Vendor details successfully", HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
