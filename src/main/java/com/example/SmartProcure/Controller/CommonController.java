package com.example.SmartProcure.Controller;

import com.example.SmartProcure.Model.AddVendorProduct;
import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import com.example.SmartProcure.Repository.ProductRepository;
import com.example.SmartProcure.Repository.VendorRepository;
import com.example.SmartProcure.Service.VendorOnboarding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    VendorController vendorController;
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

}
