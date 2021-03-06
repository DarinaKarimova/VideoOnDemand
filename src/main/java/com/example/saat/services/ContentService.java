package com.example.saat.services;


import com.example.saat.constants.ContentStatus;
import com.example.saat.models.Content;
import com.example.saat.models.ContentPage;
import com.example.saat.models.License;
import com.example.saat.repository.ContentCriteriaRepository;
import com.example.saat.repository.ContentRepository;
import com.example.saat.repository.LicenseRepository;
import com.example.saat.validators.ContentValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final ContentCriteriaRepository contentCriteriaRepository;
    private final LicenseRepository licenseRepository;
    private final ContentValidator contentValidator;

    public List<Content> getContents() {
        return contentRepository.findAll();
    }

    public String addNewContent(Content content) {
        content.setStatus(ContentStatus.InProgress);
        contentRepository.save(content);
        return ("Content has been created");
    }

    public String deleteContent(Long contentId) {
        contentValidator.isContentExists(contentId);
        contentRepository.deleteById(contentId);
        return "Content with id " +contentId + " is deleted";
    }

    public String deleteLicenseFromContent(Long contentId, Long licenseId) {
        contentValidator.isLicenseExistsInContentToDelete(contentId, licenseId);
        Content content = contentRepository.findById(contentId).get();
        License license = licenseRepository.findById(licenseId).get();
        content.getLicenses().remove(license);
        contentRepository.save(content);
        return ("License with id " + licenseId + " is deleted from content with id " + contentId);
    }

    public Content getContent(Long contentId) {
        contentValidator.isContentExists(contentId);
        return contentRepository.findById(contentId).get();
    }

    @Transactional
    public Object updateContent(Long contentId, Content contentDetails) {
        contentValidator.isContentExists(contentId);
        Content content = contentRepository.findById(contentId).get();
        content.setName(contentDetails.getName());
        content.setPosterUrl(contentDetails.getPosterUrl());
        content.setVideoUrl(contentDetails.getVideoUrl());
        content.setStatus(contentDetails.getStatus());
        content.setLicenses(contentDetails.getLicenses());
        content.setYear(contentDetails.getYear());
        content.setProvider(contentDetails.getProvider());
        content.setProcesses(contentDetails.getProcesses());
        contentRepository.save(content);
        return ("Content with id " + contentId + " is updated");
    }
    public Object enrollLicenseToContent(Long contentId, Long licenseId) {
        contentValidator.isContentExists(contentId);
        contentValidator.isLicenseExistsInContent(contentId, licenseId);
        contentValidator.isLicenseOverlappingV2(contentId, licenseId);
        Content content = contentRepository.findById(contentId).get();
        License license = licenseRepository.findById(licenseId).get();
        content.enrollLicense(license);
        Long now = System.currentTimeMillis();
        if (license.getEndTime() > now) {
            content.setStatus(ContentStatus.Published);
            contentRepository.save(content);
        }
        contentRepository.save(content);
        return ("License with id " + licenseId + " is added to content " + contentId);
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
                    newLicense.getStartTime() <= newLicense.getEndTime()) {
                return true;
            } else if (existingLicense.getStartTime() >= newLicense.getStartTime() && newLicense.getEndTime() >= existingLicense.getEndTime() &&
                    existingLicense.getStartTime() <= existingLicense.getEndTime()) {
                return true;
            }
        }
        return false;
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void scheduledTasks() {
        Long now = System.currentTimeMillis();
        List<Content> contents = contentRepository.findAll();
//        for (Content content : contents) {
//  //      content.getLicenses().stream().allMatch();
//            int expiredCounter = 0;
//            for (License license : content.getLicenses()) {
//                if (license.getEndTime() < now) {
//                    expiredCounter++;
//                }
//            }
//            if (content.getLicenses().size() == expiredCounter) {
//                content.setStatus("InProgress");
//                contentRepository.save(content);
//            }
//        }
        for (Content content : contents) {
            boolean expired = content.getLicenses().stream().allMatch(x -> x.getEndTime() < now);
            if (expired) {
                content.setStatus(ContentStatus.InProgress);
                contentRepository.save(content);
            }
        }
    }
    public Page<Content> getContentsFiltered(ContentPage contentPage, Content content){
        return contentCriteriaRepository.findAllWithFilters(contentPage, content);
    }

}











