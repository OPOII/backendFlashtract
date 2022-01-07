package com.example.demo.repository;

import com.example.demo.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVendorRepository extends JpaRepository<Vendor,Long> {
}
