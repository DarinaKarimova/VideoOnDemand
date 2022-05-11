package com.example.saat.controllers;

import com.example.saat.models.Content;
import com.example.saat.services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/content")
public class ContentController {
    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    //    @GetMapping
//    public List<Content> getContent() {
//        return contentService.getContent();
//    }
    @GetMapping
    ResponseEntity<List<Content>> getContents() {
        return new ResponseEntity<>(contentService.getContents(), HttpStatus.OK);
    }
    @GetMapping("{contentId}")
    ResponseEntity<Object> getContent(@PathVariable("contentId") Long contentId) {
        if (!contentService.contentExists(contentId)) {
            return ResponseEntity.badRequest().body("Content with id " + contentId + " does not exist");
        }
        return new ResponseEntity<>(contentService.getContent(contentId), HttpStatus.OK);
    }
//    @PostMapping
//    public void registerNewContent(@RequestBody Content content) {
//        contentService.addNewContent(content);
//    }
    @PostMapping
    ResponseEntity<Object> registerNewContent(@RequestBody Content content) {
        return new ResponseEntity<>(contentService.addNewContent(content), HttpStatus.OK);
    }
    //    @DeleteMapping(path = "{contentId}")
//    public void deleteContent(@PathVariable("contentId") Long contentId){
//        contentService.deleteContent(contentId);
//    }
    @DeleteMapping("{contentId}")
    ResponseEntity<Object> deleteContent(@PathVariable("contentId") Long contentId) {
        if (!contentService.contentExists(contentId)) {
            return ResponseEntity.badRequest().body("Content with id " + contentId + " cannot be deleted, because it does not exist");
        }
        return new ResponseEntity<>(contentService.deleteContent(contentId), HttpStatus.OK);
    }
//    @PutMapping("/{contentId}")
//    public void updateContent(@PathVariable(value = "contentId") Long contentId,
//                              @RequestBody Content contentDetails) {
//        contentService.updateContent(contentId, contentDetails);
//    }
    @PutMapping("/{contentId}")
    ResponseEntity<Object> updateContent(@PathVariable(value = "contentId") Long contentId, @RequestBody Content contentDetails) {
        if(!contentService.contentExists(contentId)){
            return ResponseEntity.badRequest().body("Content with id " + contentId + " cannot be updated, because it does not exist");
        }
        return new ResponseEntity<>(contentService.updateContent(contentId, contentDetails), HttpStatus.OK);
    }
//    @PutMapping("/{contentId}/license/{licenseId}/test")
//    public void enrollLicenseToContent(@PathVariable Long contentId, @PathVariable Long licenseId) {
//        contentService.checkContentExistence(contentId);
//        contentService.licenseOverlapping(contentId, licenseId);
    @PutMapping("/{contentId}/license/{licenseId}/add")
    ResponseEntity<Object> enrollLicenseToContent(@PathVariable Long contentId, @PathVariable Long licenseId) {
        if(!contentService.contentExists(contentId)){
            return ResponseEntity.badRequest().body("The license cannot be added, because the content with id " + contentId + " does not exist");
        }
        if(contentService.licenseOverlapping(contentId, licenseId)){
            return ResponseEntity.badRequest().body("The license with id " + licenseId + " is overlapping with existing license");
        }
        return new ResponseEntity<>(contentService.enrollLicenseToContent(contentId, licenseId), HttpStatus.OK);
    }
}







