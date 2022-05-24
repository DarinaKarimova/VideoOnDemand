package com.example.saat.controllers;

import com.example.saat.ContentSpecificationBuilder;
import com.example.saat.models.Content;
import com.example.saat.models.ContentPage;
import com.example.saat.repository.ContentRepository;
import com.example.saat.services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "api/content")
public class ContentController {
    private final ContentService contentService;
    private final ContentRepository contentRepository;

    @Autowired
    public ContentController(ContentService contentService, ContentRepository contentRepository) {
        this.contentService = contentService;
        this.contentRepository = contentRepository;
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
    @GetMapping("/pagination")
    public ResponseEntity<List<Content>> getContentPagination(@RequestParam Optional<Integer> page,
                                                              @RequestParam Optional<String> sortBy){
        Pageable paging = PageRequest.of(page.orElse(0), 5, Sort.Direction.ASC, sortBy.orElse("id"));
        List<Content> list = contentRepository.findAll(paging).getContent();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/filtered")
    public ResponseEntity<Page<Content>> getContentsFiltered(ContentPage contentPage, Content content){
        return new ResponseEntity<>(contentService.getContentsFiltered(contentPage, content), HttpStatus.OK);
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
        if(contentService.licenseExistsInContent(contentId, licenseId)){
            return ResponseEntity.badRequest().body("The license with id " + licenseId + " has already been added to content with id " + contentId);
        }
        if(contentService.licenseOverlappingV2(contentId, licenseId)){
            return ResponseEntity.badRequest().body("The license with id " + licenseId + " is overlapping with existing license");
        }
        return new ResponseEntity<>(contentService.enrollLicenseToContent(contentId, licenseId), HttpStatus.OK);
    }
    @DeleteMapping("/{contentId}/license/{licenseId}/delete")
    ResponseEntity<Object> deleteLicenseFromContent(@PathVariable Long contentId, @PathVariable Long licenseId) {
        if(!contentService.licenseExistsInContent(contentId, licenseId)){
            return ResponseEntity.badRequest().body("The license with id " + licenseId + " cannot be deleted from content " + contentId +
                    ", because the license was not added to the content");
        }
        return new ResponseEntity<>(contentService.deleteLicenseFromContent(contentId, licenseId), HttpStatus.OK);
    }
    @GetMapping("/contentFiltered")
    public List<Content> searchContent(@RequestParam(value = "search") String search){
        ContentSpecificationBuilder builder = new ContentSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()){
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Content> spec = builder.build();
        return contentRepository.findAll(spec);
    }
}







