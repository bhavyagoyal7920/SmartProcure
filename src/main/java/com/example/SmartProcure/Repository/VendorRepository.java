package com.example.SmartProcure.Repository;

import com.example.SmartProcure.DTO.SearchResultDTO;
import com.example.SmartProcure.DTO.VendorDTO;
import com.example.SmartProcure.Model.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface VendorRepository extends JpaRepository<Vendor, Long> {
    @Query("SELECT v FROM Vendor v WHERE " +
            "LOWER(v.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(v.city) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(v.gstNo) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(v.address) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "CAST(v.contactNo AS string) LIKE CONCAT('%', :query, '%') OR " +
            "LOWER(v.pincode) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(v.emailId) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(v.personOfContact1) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "CAST(v.poc1ContactNo AS string) LIKE CONCAT('%', :query, '%') OR " +
            "LOWER(v.designationOfPerson1) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(v.personOfContact2) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "CAST(v.poc2ContactNo AS string) LIKE CONCAT('%', :query, '%') OR " +
            "LOWER(v.designationOfPerson2) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(v.personOfContact3) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "CAST(v.poc3ContactNo AS string) LIKE CONCAT('%', :query, '%') OR " +
            "LOWER(v.designationOfPerson3) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(v.notes) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<SearchResultDTO> search(@Param("query")String query, Pageable pageable);
}
