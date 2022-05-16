package com.example.saat.repository;

import com.example.saat.models.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {
//    Long findAllByEndTimeLessThan(Long now);

}
