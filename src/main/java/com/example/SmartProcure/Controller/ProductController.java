package com.example.SmartProcure.Controller;

import com.example.SmartProcure.DTO.ProductDTO;
import com.example.SmartProcure.Model.AddProductResponse;
import com.example.SmartProcure.Model.Product ;
import com.example.SmartProcure.Model.Vendor;
import com.example.SmartProcure.Repository.ProductRepository;
import com.example.SmartProcure.Repository.VendorRepository;
import com.example.SmartProcure.Service.AddProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/productNames")
public class ProductController {

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AddProduct addProduct;

    //Get all the product list
    @GetMapping("/getProducts")
    public List<ProductDTO> getVendors(){
        return addProduct.getAllProducts();
    }

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

    //create a new product
    @PostMapping("/createProduct")
    public ResponseEntity<String> createProductName(@RequestBody Product product){
        try{
            productRepository.save(product);
            return new ResponseEntity<>("Product added successfully", HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //add a new product to an existing vendor
    @PostMapping("/createProductForVendor/{vendorId}")
    public ResponseEntity<String> createProductForVendor(@PathVariable(name = "vendorId") String vendorId, @RequestBody Product product){
        try{
            Product newProduct = new Product(product.getTechName(), product.getAlias(), product.getBrand(), product.getCategory());
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

    //add existing or new product to existing vendor
    @PostMapping("/addProductToVendor/{vendId}")
    public ResponseEntity<String> addProductToVendor(@PathVariable(name = "vendId") String vendId, @RequestBody AddProductResponse obj) {
        int count = 0;
        if (obj.existingProduct != null) {
            System.out.println("Existing Product: " + obj.existingProduct);
            Vendor vendor = this.vendorRepository.getReferenceById(Long.valueOf(vendId));
            Set<Product> products = new HashSet<>();
            for (String prodId : obj.existingProduct) {
                Product tempProduct = this.productRepository.getReferenceById(Long.valueOf(prodId));
                products.add(tempProduct);
            }
            vendor.setProducts(products);
            vendorRepository.save(vendor);
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
        if (obj.existingProduct == null && obj.newProduct == null) {
            return new ResponseEntity<>("Product details should exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Vendor added successfully. (No. of new products added: " + count + ")", HttpStatus.OK);
    }

    //updating product details
    @PutMapping("/updateProductDetails/{prodId}")
    public ResponseEntity<String> updateProductDetails(@RequestBody Product product, @PathVariable(name = "prodId") String prodId){
        try{
            Product existingProduct = this.productRepository.getReferenceById(Long.valueOf(prodId));
            existingProduct.setTechName(product.getTechName());
            existingProduct.setAlias(product.getAlias());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setCategory(product.getCategory());
            productRepository.save(existingProduct);
            return  new ResponseEntity<>("Updated Vendor details successfully", HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
