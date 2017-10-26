package com.lpa.spring5mvcrest.repositories;

import com.lpa.spring5mvcrest.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
