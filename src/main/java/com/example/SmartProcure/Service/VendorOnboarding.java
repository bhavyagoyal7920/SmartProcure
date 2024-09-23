package com.example.SmartProcure.Service;

import com.example.SmartProcure.DTO.VendorDTO;
import com.example.SmartProcure.Repository.VendorRepository;
import com.example.SmartProcure.Model.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendorOnboarding {

    @Autowired
    private VendorRepository vendorRepository;

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
