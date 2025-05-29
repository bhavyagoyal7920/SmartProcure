package com.example.SmartProcure.Controller;

import com.example.SmartProcure.DTO.VendorDTO;
import com.example.SmartProcure.Model.AddVendorResponse;
import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import com.example.SmartProcure.Model.VendorDetailsResponse;
import com.example.SmartProcure.Repository.CartRepository;
import com.example.SmartProcure.Repository.ProductRepository;
import com.example.SmartProcure.Repository.VendorRepository;
import com.example.SmartProcure.Service.CartService;
import com.example.SmartProcure.Service.VendorOnboarding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/vendor")
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private VendorOnboarding vendorOnboarding;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartController cartController;
    @Autowired
    CartService cartService;


    //http://localhost:8080/api/vendor/getVendors
    @GetMapping("/getVendors")
    public List<VendorDTO> getVendors() {
        return vendorOnboarding.getAllVendors();
    }

    //http://localhost:8080/api/vendor/getVendor/{vendorId}
    @GetMapping("/getVendor/{vendorId}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable(name = "vendorId") Long id) {
        try {
            VendorDTO vendorDTO = vendorOnboarding.getVendorById(id);
            return new ResponseEntity<>(vendorDTO, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public Vendor getVendorModelById(Long id){
        Vendor vendor = vendorRepository.getReferenceById(id);
        return vendor;
    }
    @GetMapping("/getVendorsProducts/{id}")
    public ResponseEntity<VendorDetailsResponse> getVendorDetails(@PathVariable(name = "vendId") String vendId) {
        Vendor vendor = this.vendorRepository.findById(Long.valueOf(vendId))
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found with ID: " + vendId));

        VendorDetailsResponse response = new VendorDetailsResponse();
        response.setVendorDetails(vendor);
        response.setExistingProductIds(
                vendor.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toList())
        );

        return ResponseEntity.ok(response);
    }

    // http://localhost:5173/api/vendor/getVendorsSorted
    @GetMapping("/getVendorsSorted")
    public ResponseEntity<Page<VendorDTO>> getVendorsSortedBy(
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try{
            Sort sort = sortOrder.equalsIgnoreCase("asc")
                    ? Sort.by(sortField).ascending()
                    : Sort.by(sortField).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Vendor> vendorPage = vendorRepository.findAll(pageable);

            Page<VendorDTO> vendorDTOPage = vendorPage.map(VendorDTO::new);

            return ResponseEntity.ok(vendorDTOPage);
        }
        catch(Exception ex){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/api/vendor/createVendor
    //save a new vendor
    @PostMapping("/createVendor")
    public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
        try {
            Vendor newVendor = new Vendor(vendor.getName(), vendor.getGstNo(), vendor.getAddress(), vendor.getCity(), vendor.getPincode(), vendor.getContactNo(), vendor.getEmailId(), vendor.getPersonOfContact1(), vendor.getPoc1ContactNo(), vendor.getPoc1EmailId(), vendor.getDesignationOfPerson1(), vendor.getPersonOfContact2(), vendor.getPoc2ContactNo(), vendor.getPoc2EmailId(), vendor.getDesignationOfPerson2(),vendor.getPersonOfContact3(), vendor.getPoc3ContactNo(), vendor.getPoc3EmailId(), vendor.getDesignationOfPerson3(), vendor.getNotes());
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
            Vendor newVendor = new Vendor(vendor.getName(), vendor.getGstNo(), vendor.getAddress(), vendor.getCity(), vendor.getPincode(), vendor.getContactNo(), vendor.getEmailId(), vendor.getPersonOfContact1(), vendor.getPoc1ContactNo(), vendor.getPoc1EmailId(), vendor.getDesignationOfPerson1(), vendor.getPersonOfContact2(), vendor.getPoc2ContactNo(), vendor.getPoc2EmailId(), vendor.getDesignationOfPerson2(), vendor.getPersonOfContact3(), vendor.getPoc3ContactNo(), vendor.getPoc3EmailId(), vendor.getDesignationOfPerson3(), vendor.getNotes());
            vendorRepository.save(newVendor);
            Product product = this.productRepository.getReferenceById(Long.valueOf(prodId));
            Set<Product> products = new HashSet<>();
            products.add(product);
            vendor.setProducts(products);
            vendorRepository.save(vendor);
            return new ResponseEntity<>("Vendor added to the product successfully!", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //add existing vendor or  new vendor to an existing product
    //http://localhost:8080/api/vendor/addVendorsToProduct/{prodId}
    @PostMapping("/addVendorsToProduct/{prodId}")
    public ResponseEntity<String> addVendorsToProduct(@PathVariable(name = "prodId") String prodId, @RequestBody AddVendorResponse obj){
        int count =0;
        boolean flag = false;
        String message = "";
        if (obj.existingVendor != null && !obj.getExistingVendor().isEmpty()) {
            System.out.println("existing Product not null");
            System.out.println("Existing Vendors: " + obj.existingVendor);
            try{
                Product product = this.productRepository.findById(Long.valueOf(prodId))
                        .orElseThrow(() -> new IllegalArgumentException("Vendor not found with ID: " + prodId));

                System.out.println("Existing Vendors IDs: " + obj.getExistingVendor());
                for(String vendId: obj.getExistingVendor()){
                    Set<Product> newProduct = new HashSet<>();
                    Vendor tempVendor = this.vendorRepository.findById(Long.valueOf(vendId))
                            .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + vendId));
                    newProduct.add(product);

                    count++;
                    System.out.println("found product id:"+prodId);

                    // Merge with existing products (if you want to add instead of replace)
                    System.out.println("Vendor's existing products: " + tempVendor.getProducts());
                    if(tempVendor.getProducts() != null) {
                        newProduct.addAll(tempVendor.getProducts());
                    }
                    System.out.println("all products:" + newProduct);

                    // Set the updated product list to the vendor
                    tempVendor.setProducts(newProduct);
                    vendorRepository.save(tempVendor);
                }
                System.out.println("Existing Vendors successfully added to the product."+ count);
            }
            catch (IllegalArgumentException e) {
                // Handle invalid vendor or product ID
                flag = true;
                message = e.getMessage();
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                // Handle unexpected errors
                flag = true;
                message = "An error occurred while adding vendors to the product: " + e.getMessage();
                System.out.println("line 179:" + e.getMessage());
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body("An error occurred while adding products to the vendor: " + e.getMessage());
            }
        } else {
            System.out.println("No existing vendors provided.");
        }

        if (obj.newVendors != null) {
            System.out.println("New Vendor: " + obj.newVendors);

            for (Vendor vendor : obj.getNewVendors()) {
                ResponseEntity<String> res = createVendorForProduct(vendor, prodId);
                if (res.getStatusCode() == HttpStatus.OK) {
                    count++;
                }
            }

        } else {
            System.out.println("No new vendors provided.");
        }
//        if (obj.existingProduct == null && obj.newProduct == null) {
//            return new ResponseEntity<>("Product details should exists", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        if(flag){
            return new ResponseEntity<>("error occured:" + message , HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>("Product added successfully. (No. of new vendors added: " + count + ")", HttpStatus.OK);
        }
    }

//    @PostMapping("/addVendorsToProduct/{prodId}")
//    public ResponseEntity<String> addVendorsToProduct(@PathVariable(name = "prodId") String prodId, @RequestBody AddVendorResponse obj) {
//        int count = 0;
//        boolean flag = false;
//        String message = "";
//        // Fetch product safely
//        Optional<Product> productOpt = productRepository.findById(Long.valueOf(prodId));
//        if (!productOpt.isPresent()) {
//            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
//        }
//        Product product = productOpt.get();
//
//        // Add existing vendors
//        if (obj.existingVendor != null) {
//            Set<Vendor> vendors = new HashSet<>();
//            for (String vendId : obj.existingVendor) {
//                Optional<Vendor> tempVendorOpt = vendorRepository.findById(Long.valueOf(vendId));
//                if (tempVendorOpt.isPresent()) {
//                    vendors.add(tempVendorOpt.get());
//                    count++;
//                } else {
//                    return new ResponseEntity<>("Vendor with ID " + vendId + " not found", HttpStatus.NOT_FOUND);
//                }
//            }
//            product.getVendors().addAll(vendors);
////            Set<Vendor> vendorsT = product.getVendors();
////            for(Vendor vendor: vendorsT){
////                System.out.println(vendor.getId());
////            }
//        }
//
//        // Add new vendors
//        if (obj.newVendors != null) {
//            for (Vendor vendor : obj.getNewVendors()) {
//                ResponseEntity<String> res = createVendorForProduct(vendor, prodId);
//                if (res.getStatusCode() == HttpStatus.OK) {
//                    count++;
//                } else {
//                    return new ResponseEntity<>("Error creating vendor: " + res.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
//                }
//            }
//        }
//
//        // Save product with updated vendors
//        productRepository.save(product);
//        Set<Vendor> vendorsT = product.getVendors();
//        for(Vendor vendor: vendorsT){
//            System.out.println(vendor.getId());
//        }
//
//        if (obj.existingVendor == null && obj.newVendors == null) {
//            return new ResponseEntity<>("Vendor details should exist", HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<>("Product updated successfully. (New vendors added: " + count + ")", HttpStatus.OK);
//    }


    //update details of a an existing Vendor
    @PutMapping("/updateVendorDetails/{vendId}")
    public ResponseEntity<String> updateVendorDetails(@RequestBody Vendor vendor, @PathVariable(name = "vendId") String vendId){
        try{
            Vendor existingVendor = this.vendorRepository.getReferenceById(Long.valueOf(vendId));
            existingVendor.setName(vendor.getName());
            existingVendor.setGstNo(vendor.getGstNo());
            existingVendor.setAddress(vendor.getAddress());
            existingVendor.setCity(vendor.getCity());
            existingVendor.setPincode(vendor.getPincode());
            existingVendor.setContactNo(vendor.getContactNo());
            existingVendor.setEmailId(vendor.getEmailId());
            existingVendor.setPersonOfContact1(vendor.getPersonOfContact1());
            existingVendor.setPoc1ContactNo(vendor.getPoc1ContactNo());
            existingVendor.setDesignationOfPerson1(vendor.getDesignationOfPerson1());
            existingVendor.setPersonOfContact2(vendor.getPersonOfContact2());
            existingVendor.setPoc2ContactNo(vendor.getPoc2ContactNo());
            existingVendor.setDesignationOfPerson2(vendor.getDesignationOfPerson2());
            existingVendor.setPersonOfContact3(vendor.getPersonOfContact3());
            existingVendor.setPoc3ContactNo(vendor.getPoc3ContactNo());
            existingVendor.setDesignationOfPerson3(vendor.getDesignationOfPerson3());
            existingVendor.setNotes(vendor.getNotes());
//            existingVendor.setFrequencyOfPurchase(vendor.getFrequencyOfPurchase());
//            existingVendor.setLastPurchasedOn(vendor.getLastPurchasedOn());
            vendorRepository.save(existingVendor);
            return  new ResponseEntity<>("Updated Vendor details successfully", HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteVendor/{vendId}")
    public ResponseEntity<String> deleteVendor(@PathVariable(name = "vendId") String vendId){
        try{
            Vendor vendor = this.vendorRepository.getReferenceById(Long.valueOf(vendId));
//            System.out.println("checking for vendor in cart");
            if(cartService.itemExists(Long.valueOf(vendId))) {
//                System.out.println("vendor found in cart");
                ResponseEntity<String> res = cartController.removeVendorFromCart(Long.valueOf(vendId));
//                System.out.println(res);
            }
            vendorRepository.delete(vendor);
            return new ResponseEntity<>("Deleted Vendor Successfully", HttpStatus.OK);
        }
        catch (Exception ex){
            System.out.println("could not delete"+ ex);
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
