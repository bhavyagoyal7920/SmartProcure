package com.example.SmartProcure.Service;

import com.example.SmartProcure.DTO.SearchResultDTO;
import com.example.SmartProcure.Model.Product;
import com.example.SmartProcure.Model.Vendor;
import com.example.SmartProcure.Repository.ProductRepository;
import com.example.SmartProcure.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    ProductRepository productRepository;

    public Page<SearchResultDTO> search(String query, Pageable pageble){

        Page<SearchResultDTO> vendorResults = vendorRepository.search(query, pageble);
        Page<SearchResultDTO> productResults = productRepository.search(query, pageble);

        List<SearchResultDTO> results = new ArrayList<>();
        vendorResults.forEach(results::add);
        productResults.forEach(results::add);

        int start = pageble.getPageNumber()* pageble.getPageSize();
        int end = Math.min(start + pageble.getPageSize(), results.size());

        List<SearchResultDTO> pageContent = results.subList(start, end);

        return new PageImpl<>(pageContent, pageble, results.size());

//        List<Vendor> vendors = vendorRepository.searchVendors(query);
//        for(Vendor vendor : vendors){
//            for(Product product: vendor.getProducts()){
//                results.add(new SearchResultDTO(
//                        vendor.getId(),
//                        vendor.getName(),
//                        product.getId(),
//                        product.getTechName(),
//                        product.getAlias(),
//                        product.getMake(),
//                        product.getCategory()
//                ));
//            }
//        }
//
//        List<Product> products = productRepository.searchProducts(query);
//        for(Product product : products){
//            for(Vendor vendor : product.getVendors()){
//                results.add(new SearchResultDTO(
//                        vendor.getId(),
//                        vendor.getName(),
//                        product.getId(),
//                        product.getTechName(),
//                        product.getAlias(),
//                        product.getMake(),
//                        product.getCategory()
//                ));
//            }
//        }
//        return results;
    }


}