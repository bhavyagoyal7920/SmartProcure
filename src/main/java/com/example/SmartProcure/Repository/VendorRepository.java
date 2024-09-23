package com.example.SmartProcure.Repository;

import com.example.SmartProcure.DTO.VendorDTO;
import com.example.SmartProcure.Model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
