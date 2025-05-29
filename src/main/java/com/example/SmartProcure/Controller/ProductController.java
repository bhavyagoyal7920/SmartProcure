package com.example.SmartProcure.Controller;

import com.example.SmartProcure.DTO.ProductDTO;
import com.example.SmartProcure.DTO.VendorDTO;
import com.example.SmartProcure.Model.AddProductResponse;
import com.example.SmartProcure.Model.Product ;
import com.example.SmartProcure.Model.Vendor;
import com.example.SmartProcure.Repository.ProductRepository;
import com.example.SmartProcure.Repository.VendorRepository;
import com.example.SmartProcure.Service.AddProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/productNames")
public class ProductController {

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AddProduct addProduct;

    //http://localhost:8080/api/productNames/getProducts
    //Get all the product list
    @GetMapping("/getProducts")
    public List<ProductDTO> getVendors(){
        return addProduct.getAllProducts();
    }

    //http://localhost:8080/api/productNames/getProduct/{id}
    //Get product details by id
    @GetMapping("/getProduct/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable(name = "id") Long id){
        try{
            ProductDTO productDTO = addProduct.getProductById(id);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        }catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // http://localhost:8080/api/productNames/getProductsSorted
    //get sorted product list by id
    @GetMapping("/getProductsSorted")
    public ResponseEntity<Page<ProductDTO>> getProductsSortedBy(
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
            Page<Product> productPage = productRepository.findAll(pageable);

            Page<ProductDTO> productDTOPage = productPage.map(ProductDTO::new);

            return ResponseEntity.ok(productDTOPage);
        }
        catch(Exception ex){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/api/productNames/createProduct
    //create a new product
    @PostMapping("/createProduct")
    public ResponseEntity<String> createProductName(@Validated @RequestBody Product product){
        try{
            productRepository.save(product);
            return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/api/productNames/createProductForVendor/{vendorId}
    //add a new product to an existing vendor
    @PostMapping("/createProductForVendor/{vendorId}")
    public ResponseEntity<String> createProductForVendor(@PathVariable(name = "vendorId") String vendorId, @RequestBody Product product){
        try{
            Product newProduct = new Product(product.getTechName(), product.getAlias(), product.getMake(), product.getCategory(), product.getDescription(), product.getNotes());
            productRepository.save(newProduct);

            Vendor vendor = this.vendorRepository.getReferenceById(Long.valueOf(vendorId));

            Set<Product> products = new HashSet<>();
            products.add(newProduct);
            vendor.setProducts(products);
            vendorRepository.save(vendor);
            return new ResponseEntity<>("Product added to the vendor successfully!", HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //http://localhost:8080/api/productNames/addProductToVendor/{vendId}
    //add existing or new product to existing vendor
    @PostMapping("/addProductToVendor/{vendId}")
    public ResponseEntity<String> addProductToVendor(@PathVariable(name = "vendId") String vendId, @RequestBody AddProductResponse obj) {
        int count = 0;
        System.out.println(count);
        boolean flag = false;
        String message = "";
        if (obj.getExistingProduct() != null && !obj.getExistingProduct().isEmpty()) {
            System.out.println("existing Product not null");
            try {
                // Check if the vendor ID is valid
                Vendor vendor = this.vendorRepository.findById(Long.valueOf(vendId))
                        .orElseThrow(() -> new IllegalArgumentException("Vendor not found with ID: " + vendId));

                // Initialize a set to hold the products to be added
                Set<Product> newProducts = new HashSet<>();

//                if (obj.getExistingProduct() != null && !obj.getExistingProduct().isEmpty()) {
                System.out.println("Existing Product IDs: " + obj.getExistingProduct());

                // Loop through product IDs and fetch corresponding products
                for (String prodId : obj.getExistingProduct()) {
                    Product tempProduct = this.productRepository.findById(Long.valueOf(prodId))
                            .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + prodId));
                    newProducts.add(tempProduct);
                    count++;
                    System.out.println("found product id:"+prodId);
                }

                // Merge with existing products (if you want to add instead of replace)
                System.out.println("Vendor's existing products: " + vendor.getProducts());
                if(vendor.getProducts() != null) {
                    newProducts.addAll(vendor.getProducts());
                }
                System.out.println("all products:" + newProducts);

                // Set the updated product list to the vendor
                vendor.setProducts(newProducts);
                vendorRepository.save(vendor);

                System.out.println("Products successfully added to the vendor.");
            } catch (IllegalArgumentException e) {
                // Handle invalid vendor or product ID
                flag = true;
                message = e.getMessage();
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (Exception e) {
                // Handle unexpected errors
                flag = true;
                message = "An error occurred while adding products to the vendor: " + e.getMessage();
                System.out.println("line 168:" + e.getMessage());
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body("An error occurred while adding products to the vendor: " + e.getMessage());
            }
        } else {
            System.out.println("No existing products provided.");
        }

        if (obj.newProduct != null) {
            System.out.println("New Product: " + obj.newProduct);

            for (Product product : obj.getNewProduct()) {
                ResponseEntity<String> res = createProductForVendor(vendId, product);
                if (res.getStatusCode() == HttpStatus.OK) {
                    count++;
                }
            }

        } else {
            System.out.println("No new products provided.");
        }
//        if (obj.existingProduct == null && obj.newProduct == null) {
//            return new ResponseEntity<>("Product details should exists", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        if(flag){
            return new ResponseEntity<>("error occured:" + message , HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>("Vendor added successfully. (No. of new products added: " + count + ")", HttpStatus.OK);
        }
    }

    //http://localhost:8080/api/productNames/updateProductDetails/{prodId}
    //updating product details
    @PutMapping("/updateProductDetails/{prodId}")
    public ResponseEntity<String> updateProductDetails(@RequestBody Product product, @PathVariable(name = "prodId") String prodId){
        try{
            Product existingProduct = this.productRepository.getReferenceById(Long.valueOf(prodId));
            existingProduct.setTechName(product.getTechName());
            existingProduct.setAlias(product.getAlias());
            existingProduct.setMake(product.getMake());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setDescription((product.getDescription()));
            existingProduct.setNotes(product.getNotes());
            productRepository.save(existingProduct);
            return  new ResponseEntity<>("Updated Vendor details successfully", HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/api/productNames/deleteProduct/{prodId}
    //delete a product
    @DeleteMapping("/deleteProduct/{prodId}")
    public ResponseEntity<String> deleteVendor(@PathVariable(name = "prodId") String prodId){

        return addProduct.deleteProduct(Long.valueOf(prodId));
    }

}
