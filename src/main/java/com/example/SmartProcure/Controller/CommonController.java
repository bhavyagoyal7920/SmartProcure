package com.example.SmartProcure.Controller;

import com.example.SmartProcure.DTO.SearchResultDTO;
import com.example.SmartProcure.Model.AddProductResponse;
import com.example.SmartProcure.Model.AddVendorProduct;
import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import com.example.SmartProcure.Repository.ProductRepository;
import com.example.SmartProcure.Repository.VendorRepository;
import com.example.SmartProcure.Service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductController productController;

    @Autowired
    SearchService searchService;


    //add a new Vendor and a new product
    @PostMapping("/newVendorAndProduct")
    public ResponseEntity<String> addNewVendorAndProduct(@RequestBody AddVendorProduct addVendorProduct){

//        System.out.println(addVendorProduct.newProduct);
//        System.out.println(addVendorProduct.existingProduct);
//        System.out.println(addVendorProduct.vendor);

        if(addVendorProduct.vendor !=null ){
            System.out.println("Adding new vendor:"+addVendorProduct.vendor);

            Vendor tempVendor = vendorRepository.save(addVendorProduct.vendor);
            System.out.println(tempVendor);

            String venId = Long.toString(tempVendor.getId());
            System.out.println(venId);
            AddProductResponse obj = new AddProductResponse();
            obj.existingProduct = addVendorProduct.existingProduct;
            obj.newProduct = addVendorProduct.newProduct;
            System.out.println("existing product:"+ obj.existingProduct);
            System.out.println("new product:"+ obj.newProduct);
            return productController.addProductToVendor(venId, obj);
        }
        else{
            return new ResponseEntity<>("Vendor details missing.", HttpStatus.BAD_REQUEST);
        }
    }



    //delete a product from a vendors list or vice versa
    // http://localhost:8080/api/common/deleteProductVendorMapping/{selectedVendorId}/{selectedProductId}
    @DeleteMapping("/deleteProductVendorMapping/{selectedVendorId}/{selectedProductId}")
    public ResponseEntity<String> deleteProductVendorMapping(@PathVariable(name = "selectedVendorId") String vendId, @PathVariable(name = "selectedProductId") String prodId){

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

    //http://localhost:8080/api/common/search
    //search engine
    @GetMapping("/search")
    public ResponseEntity<Page<SearchResultDTO>> search(
            @RequestParam("query") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
    ) {
        try {
            Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            System.out.println("Search Query: " + query);
            System.out.println("Page: " + page);
            System.out.println("Size: " + size);
            System.out.println("Sort By: " + sortBy);
            System.out.println("Sort Order: " + sortOrder);
            Page<SearchResultDTO> results = searchService.search(query, pageable);
            return ResponseEntity.ok(results);
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
