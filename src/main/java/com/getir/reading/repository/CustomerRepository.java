package com.getir.reading.repository;

import com.getir.reading.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Customer findByEmail(String email);
}
