package com.example.SmartProcure.Repository;

import com.example.SmartProcure.Model.QuotationForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuotationFormRepository extends JpaRepository<QuotationForm, Long> {
    List<QuotationForm> findByVendorId(Long vendorId);
}
