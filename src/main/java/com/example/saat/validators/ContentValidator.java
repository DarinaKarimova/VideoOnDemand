package com.example.saat.validators;

import com.example.saat.models.Content;
import com.example.saat.models.License;
import com.example.saat.repository.ContentRepository;
import com.example.saat.repository.LicenseRepository;
import exceptions.BadRequestException;
import exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ContentValidator {
    private ContentRepository contentRepository;
    private LicenseRepository licenseRepository;

    public void isContentExists(Long contentId){
        if(!contentRepository.existsById(contentId))
            throw new ResourceNotFoundException("Content with id " + contentId + " does not exist");
    }

    public void isLicenseExistsInContent(Long contentId, Long licenseId){
        Content content = contentRepository.findById(contentId).get();
        License license = licenseRepository.findById(licenseId).get();
        if(content.getLicenses().contains(license))
            throw new BadRequestException("The license with id " + licenseId + " exists in content with id " + contentId);
    }

    public void isLicenseExistsInContentToDelete(Long contentId, Long licenseId) {
        Content content = contentRepository.findById(contentId).get();
        License license = licenseRepository.findById(licenseId).get();
        if(!content.getLicenses().contains(license))
            throw new BadRequestException("The license with id " + licenseId + " cannot be removed from content with id " +
                    contentId + ", because content does not contain this license");
    }

    public void isLicenseOverlappingV2(Long contentId, Long licenseId) {
        Content content = contentRepository.findById(contentId).get();
        License newLicense = licenseRepository.findById(licenseId).get();
        for (License existingLicense : content.getLicenses()) {
            if (betweenLicenseWindow(newLicense.getStartTime(), existingLicense)) { //new.startTime in existed check
                throw new BadRequestException("The license with id " + licenseId + " is overlapping with existing license");
            } else if (betweenLicenseWindow(newLicense.getEndTime(), existingLicense)) { //new.endTime in existed check
                throw new BadRequestException("The license with id " + licenseId + " is overlapping with existing license");
            } else if (betweenLicenseWindow(existingLicense.getStartTime(), newLicense)) { //existed.startTime in new check
                throw new BadRequestException("The license with id " + licenseId + " is overlapping with existing license");
            } else if (betweenLicenseWindow(existingLicense.getEndTime(), newLicense)) { //existed.endTime in new check
                throw new BadRequestException("The license with id " + licenseId + " is overlapping with existing license");
            }
        }
    }
    private boolean betweenLicenseWindow(long variable, License license) {
        return variable >= license.getStartTime() && variable <= license.getEndTime();
    }
}
