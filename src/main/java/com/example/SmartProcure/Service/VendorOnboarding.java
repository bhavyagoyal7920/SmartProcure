package com.example.SmartProcure.Service;

import com.example.SmartProcure.DTO.VendorDTO;
import com.example.SmartProcure.Repository.ProductRepository;
import com.example.SmartProcure.Repository.VendorRepository;
import com.example.SmartProcure.Model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendorOnboarding {

    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<VendorDTO> getAllVendors(){
        List<Vendor> vendors = vendorRepository.findAll();
        List<VendorDTO> vendorDTOS = new ArrayList<>();
        for(Vendor vendor : vendors){
            VendorDTO vendorDTO = new VendorDTO(vendor);
            vendorDTOS.add(vendorDTO);
        }
        return vendorDTOS;
    }

    public VendorDTO getVendorById(Long id){
        Vendor vendor = vendorRepository.getReferenceById(id);
        VendorDTO vendorDTO = new VendorDTO(vendor);
        return vendorDTO;

    }


}
