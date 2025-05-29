package com.example.SmartProcure.Repository;

import com.example.SmartProcure.DTO.SearchResultDTO;
import com.example.SmartProcure.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.techName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.alias) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.make) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.notes) LIKE LOWER(CONCAT('%', :query, '%'))")

    Page<SearchResultDTO> search(@Param("query")String query, Pageable pageable);

}
