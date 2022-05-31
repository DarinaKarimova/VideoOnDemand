package com.example.saat.validators;

import com.example.saat.models.Content;
import com.example.saat.models.License;
import com.example.saat.repository.ContentRepository;
import com.example.saat.repository.LicenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ContentValidator {
    private ContentRepository contentRepository;
    private LicenseRepository licenseRepository;

    public Object isContentExists(Long contentId){
        if(!contentRepository.existsById(contentId))
            return ("Content with id " + contentId + " does not exist");
        return true;
    }

    public Object isLicenseExistsInContent(Long contentId, Long licenseId){
        Content content = contentRepository.findById(contentId).get();
        License license = licenseRepository.findById(licenseId).get();
        if(content.getLicenses().contains(license))
            return "The license with id " + licenseId + " exists in content with id " + contentId;
        return false;
    }

    public Object isLicenseOverlappingV2(Long contentId, Long licenseId) {
        Content content = contentRepository.findById(contentId).get();
        License newLicense = licenseRepository.findById(licenseId).get();
        if (content.getLicenses().isEmpty()) {
            return false;
        }
        for (License existingLicense : content.getLicenses()) {
            if (betweenLicenseWindow(newLicense.getStartTime(), existingLicense)) { //new.startTime in existed check
                return "The license with id " + licenseId + " is overlapping with existing license";
            } else if (betweenLicenseWindow(newLicense.getEndTime(), existingLicense)) { //new.endTime in existed check
                return "The license with id " + licenseId + " is overlapping with existing license";
            } else if (betweenLicenseWindow(existingLicense.getStartTime(), newLicense)) { //existed.startTime in new check
                return "The license with id " + licenseId + " is overlapping with existing license";
            } else if (betweenLicenseWindow(existingLicense.getEndTime(), newLicense)) { //existed.endTime in new check
                return "The license with id " + licenseId + " is overlapping with existing license";
            }
        }
        return false;
    }
    private boolean betweenLicenseWindow(long variable, License license) {
        return variable >= license.getStartTime() && variable <= license.getEndTime();
    }
}
