package com.example.SmartProcure.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.SmartProcure.DTO.CartDTO;
import com.example.SmartProcure.Model.Cart;
import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.CartProductData;
import com.example.SmartProcure.Repository.CartRepository;
import com.example.SmartProcure.Repository.ProductRepository;
import com.example.SmartProcure.Repository.VendorRepository;
import com.example.SmartProcure.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    CartService cartService;

    public CartController(CartService cartService){
        this.cartService=cartService;
    }

    //http://localhost:8080/api/cart/getItems
    @GetMapping("/getItems")
    public List<Cart> getCartItems(){
        return cartService.getCartItems();
    }

    //http://localhost:8080/api/cart/addAskQuotation
    @PostMapping("/addAskQuotation")
    public ResponseEntity<String> addAskQuotation(@RequestBody CartDTO cartDTO){
        try{
            Cart existingCart = cartService.getCartItemsByVendorId(cartDTO.getVendorId());
            if(existingCart == null){
                existingCart = new Cart();
                existingCart.setVendorId(cartDTO.getVendorId());
                existingCart.setVendorName(vendorRepository.getReferenceById(cartDTO.getVendorId()).getName());
                existingCart.setProducts(new ArrayList<>());
            }
            Product product = productRepository.getReferenceById(cartDTO.getProductId());
            CartProductData productData = new CartProductData();
            productData.setCart(existingCart);
            productData.setProductId(product.getId());
            productData.setProductName(product.getTechName());
            productData.setAlias(product.getAlias());
            productData.setMake(product.getMake());
            productData.setQuantityRequired(cartDTO.getQuantityRequired());
            productData.setNote(cartDTO.getNote());

            existingCart.getProducts().add(productData);
            cartRepository.save(existingCart);
            return new ResponseEntity<>("Product successfully added to cart", HttpStatus.OK);
        }catch(Exception ex){
            return  new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/api/cart/update
    @PutMapping("/update")
    public ResponseEntity<String> updateDetails(@RequestBody CartDTO cartDTO){
        try{
            Long vendorId = cartDTO.getVendorId();
            Long productId = cartDTO.getProductId();
            System.out.println(vendorId+" "+productId);
            Cart cart = cartService.getCartItemsByVendorId(vendorId); //getting existing cart value for this vendorId
            System.out.println(cart.getVendorId());
            List<CartProductData> existingData = cart.getProducts(); // getting existing products list for vendor in cart
            CartProductData updated = new CartProductData();
            for (CartProductData c : existingData) {
                if (Objects.equals(c.getProductId(), productId)) {
                    updated = c;
                }
            }
            System.out.println(updated.getProductId());
//            Boolean res = existingData.remove(c);
//            System.out.println(res);
            updated.setQuantityRequired(cartDTO.getQuantityRequired());
            updated.setNote(cartDTO.getNote());
            existingData.add(updated);
            cart.setProducts(existingData);
            cartRepository.save(cart);
            return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
        }
        catch(Exception ex){
            System.out.println(ex.toString());
            return new ResponseEntity<>("Failed to update.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/api/cart/removeProduct/{vendorId}/{productId}
    @DeleteMapping("/removeProduct/{vendorId}/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable(name = "vendorId") Long vendorId,
                                      @PathVariable(name = "productId") Long productId){

        Cart cart = cartService.getCartItemsByVendorId(vendorId);
        if(cart == null){
            return new ResponseEntity<>("there are no quotations for this vendor",HttpStatus.NOT_FOUND);
        }
        try{
            List<CartProductData> productData = cart.getProducts();
            for(CartProductData c : productData){
                if(Objects.equals(c.getProductId(), productId)){
                    productData.remove(c);
                    cart.setProducts(productData);
                    cartRepository.save(cart);
                }
            }
            return new ResponseEntity<>("Product successfully removed from the list", HttpStatus.OK);
        }
        catch(Exception ex){
            return  new ResponseEntity<>("Failed to delete the product from the list", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/api/cart/removeProduct/{vendorId}
    @DeleteMapping("/removeProduct/{vendorId}")
    public ResponseEntity<String> removeVendorFromCart(@PathVariable(name = "vendorId") Long vendorId){
        try{
            System.out.println("in here");
            Cart cartItem = cartService.getCartItemsByVendorId(vendorId);
//            for(Cart cart: cartItems){
//                System.out.println(cart.getVendorId());
                cartRepository.deleteById(cartItem.getId());
//            }
//            cartRepository.deleteAllById(Collections.singleton(vendorId));
            return new ResponseEntity<>("Vendor deleted successfully from cart.", HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>("Failed to remove vendor from cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
