package com.example.SmartProcure.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.example.SmartProcure.DTO.CartDTO;
import com.example.SmartProcure.Model.Cart;
import com.example.SmartProcure.Model.Vendor;
import com.example.SmartProcure.Repository.CartRepository;
import com.example.SmartProcure.Repository.ProductRepository;
import com.example.SmartProcure.Repository.VendorRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final VendorRepository vendorRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository, VendorRepository vendorRepository, ProductRepository productRepository){
        this.cartRepository = cartRepository;
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;
    }
    public List<Cart> getCartItems(){
        return cartRepository.findAll();
    }

    public boolean itemExists(Long vendorId){
        List<Cart> items = getCartItems();
        for(Cart item : items){
            if(Objects.equals(item.getVendorId(), vendorId)){
                return true;
            }
        }
        return false;
    }
    public Cart getCartItemsByVendorId(Long vendorId){
        List<Cart> cartItems = cartRepository.findAll();
        for(Cart cartItem: cartItems){
            if(Objects.equals(cartItem.getVendorId(), vendorId)){
                return cartItem;
            }
        }
         return null;
    }


}
