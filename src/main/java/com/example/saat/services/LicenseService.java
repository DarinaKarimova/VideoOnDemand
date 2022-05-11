package com.example.saat.services;

import com.example.saat.models.License;
import com.example.saat.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LicenseService {
    private final LicenseRepository licenseRepository;
    @Autowired
    public LicenseService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public List<License> getLicenses() {
        return licenseRepository.findAll();
    }

    public License getLicense(Long licenseId){
        return licenseRepository.findById(licenseId).get();
    }
    public String addNewLicense(License license) {
        licenseRepository.save(license);
        return ("License has been created");
    }

    public String deleteLicense(Long licenseId) {
//        boolean exists = licenseRepository.existsById(licenseId);
//        if(!exists){
//            throw new IllegalStateException("license with id: " + licenseId + " does not exist");
//        }
        licenseRepository.deleteById(licenseId);
        return ("License with id " + licenseId + " is deleted");
    }
   @Transactional
    public String updateLicense(Long licenseId, License licenseDetails){
        License license = licenseRepository.findById(licenseId)
                .orElseThrow(()-> new IllegalStateException("License not found: " + licenseId));
        license.setName(licenseDetails.getName());
        license.setStartTime(licenseDetails.getStartTime());
        license.setEndTime(licenseDetails.getEndTime());
        licenseRepository.save(license);
        return ("License with id " + licenseId + " has been updated");
   }

    public boolean licenseExists(Long licenseId){
        return licenseRepository.existsById(licenseId);
    }
}
