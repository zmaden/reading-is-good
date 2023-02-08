package com.getir.reading.repository;

import com.getir.reading.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {

    Stock findByCode(String code);
}
