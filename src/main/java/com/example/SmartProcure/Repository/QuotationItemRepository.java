package com.example.SmartProcure.Repository;

import com.example.SmartProcure.Model.QuotationItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotationItemRepository extends JpaRepository<QuotationItems, Long> {
}
