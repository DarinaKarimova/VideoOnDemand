package com.example.saat.services;


import com.example.saat.models.Content;
import com.example.saat.models.License;
import com.example.saat.repository.ContentRepository;
import com.example.saat.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContentService {
    private final ContentRepository contentRepository;
    private final LicenseRepository licenseRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository, LicenseRepository licenseRepository) {
        this.contentRepository = contentRepository;
        this.licenseRepository = licenseRepository;
    }
    public List<Content> getContents() {
        return contentRepository.findAll();
    }
    public String addNewContent(Content content) {
        content.setStatus("InProgress");
        contentRepository.save(content);
        return("Content has been created");
    }
    public String deleteContent(Long contentId) {
        contentRepository.deleteById(contentId);
        return ("Content with id " + contentId + " is deleted");
    }
    public String deleteLicenseFromContent(Long contentId, Long licenseId) {
        Content content = contentRepository.findById(contentId).get();
        License license = licenseRepository.findById(licenseId).get();
        content.getLicenses().remove(license);
        return ("License with id " + licenseId + " is deleted from content with id " + contentId);
    }
    public Content getContent(Long contentId) {
        return contentRepository.findById(contentId).get();
    }
    @Transactional
    public String updateContent(Long contentId, Content contentDetails) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalStateException("Content not found: " + contentId));
        content.setName(contentDetails.getName());
        content.setPosterUrl(contentDetails.getPosterUrl());
        content.setVideoUrl(contentDetails.getVideoUrl());
        content.setStatus(contentDetails.getStatus());
        content.setLicenses(contentDetails.getLicenses());
        contentRepository.save(content);
        return ("Content with id " + contentId + " is updated");
    }
    public String enrollLicenseToContent(Long contentId, Long licenseId) {
        Content content = contentRepository.findById(contentId).get();
        License license = licenseRepository.findById(licenseId).get();
        content.enrollLicense(license);
        contentRepository.save(content);
        return("License with id " + licenseId + " is added to content " + contentId);
    }
    public boolean contentExists(Long contentId) {
        return contentRepository.existsById(contentId);
    }
    public boolean licenseExistsInContent(Long contentId, Long licenseId){
        Content content = contentRepository.findById(contentId).get();
        License license = licenseRepository.findById(licenseId).get();
        return content.getLicenses().contains(license);
    }
    public boolean licenseOverlapping(Long contentId, Long licenseId) {
        Content content = contentRepository.findById(contentId).get();
        License newLicense = licenseRepository.findById(licenseId).get();
        if (content.getLicenses().isEmpty()) {
            return false;
        }
        for (License existingLicense : content.getLicenses()) {
            if (existingLicense.getStartTime() <= newLicense.getStartTime() && existingLicense.getEndTime() <= newLicense.getEndTime() &&
                    newLicense.getStartTime() <= existingLicense.getEndTime()) {
                return true;
            } else if (existingLicense.getStartTime() >= newLicense.getStartTime() && existingLicense.getEndTime() >= newLicense.getEndTime() &&
                    existingLicense.getStartTime() <= newLicense.getEndTime()) {
                return true;
            } else if (existingLicense.getStartTime() <= newLicense.getStartTime() && newLicense.getEndTime() <= existingLicense.getEndTime() &&
                    newLicense.getStartTime()<= newLicense.getEndTime()) {
                return true;
            } else if (existingLicense.getStartTime() >= newLicense.getStartTime() && newLicense.getEndTime() >= existingLicense.getEndTime() &&
                    existingLicense.getStartTime()<= existingLicense.getEndTime()) {
                return true;
            }
        }
        return false;
    }
}











