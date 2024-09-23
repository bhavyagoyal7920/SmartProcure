package com.example.SmartProcure.Controller;

import com.example.SmartProcure.Model.AddVendorProduct;
import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import com.example.SmartProcure.Repository.ProductRepository;
import com.example.SmartProcure.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductController productController;


    //add a new Vendor and a new product
    @PostMapping("/newVendorAndProduct")
    public ResponseEntity<String> addNewVendorAndProduct(@RequestBody AddVendorProduct addVendorProduct){

        System.out.println(addVendorProduct.product);
        System.out.println(addVendorProduct.vendor);

        if(addVendorProduct.vendor !=null && addVendorProduct.product !=null){
            System.out.println("Adding new vendor:"+addVendorProduct.vendor);

            Vendor tempVendor = vendorRepository.save(addVendorProduct.vendor);
            System.out.println(tempVendor);

            String venId = Long.toString(tempVendor.getId());
            System.out.println(venId);

            return productController.createProductForVendor(venId, addVendorProduct.product);
        }
        else{
            return new ResponseEntity<>("Product or Vendor details missing.", HttpStatus.BAD_REQUEST);
        }
    }



    //delete a product from a vendors list or vice versa
    @DeleteMapping("/deleteProductVendorMapping/{vendId}/{prodId}")
    public ResponseEntity<String> deleteProductVendorMapping(@PathVariable(name = "vendId") String vendId, @PathVariable(name = "prodId") String prodId){

        Vendor vendor;
        Product product;
        // Step 1: Fetch the vendor using the vendor service or repository
        try{
            vendor = vendorRepository.getReferenceById(Long.valueOf(vendId));
        }
        catch(Exception ex){
            return new ResponseEntity<>("Vendor not found", HttpStatus.NOT_FOUND);
        }
        try{
            product = productRepository.getReferenceById(Long.valueOf(prodId));
        }
        catch(Exception ex){
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

        // Step 3: Check if the product exists in the vendor's list of products
        if (!vendor.getProducts().contains(product)) {
             return new ResponseEntity<>("Product not associated with vendor", HttpStatus.BAD_REQUEST);
        }

        // Step 4: Remove the product from the vendor's list
        vendor.getProducts().remove(product);

        // Step 5: Save the vendor (or alternatively save the product if the association is bi-directional)
        vendorRepository.save(vendor);

        // Return success message
        return new ResponseEntity<>("Product successfully removed from vendor", HttpStatus.OK);

    }

}
