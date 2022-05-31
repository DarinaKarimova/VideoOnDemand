package com.example.saat.controllers;

import com.example.saat.Specifications.ContentSpecificationBuilder;
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

    @GetMapping
    ResponseEntity<List<Content>> getContents() {
        return new ResponseEntity<>(contentService.getContents(), HttpStatus.OK);
    }

    @GetMapping("{contentId}")
    ResponseEntity<Object> getContent(@PathVariable("contentId") Long contentId) {
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

    @PostMapping
    ResponseEntity<Object> registerNewContent(@RequestBody Content content) {
        return new ResponseEntity<>(contentService.addNewContent(content), HttpStatus.OK);
    }

    @DeleteMapping("{contentId}")
    ResponseEntity<Object> deleteContent(@PathVariable("contentId") Long contentId) {
        return new ResponseEntity<>(contentService.deleteContent(contentId), HttpStatus.OK);
    }

    @PutMapping("/{contentId}")
    ResponseEntity<Object> updateContent(@PathVariable(value = "contentId") Long contentId, @RequestBody Content contentDetails) {
        return new ResponseEntity<>(contentService.updateContent(contentId, contentDetails), HttpStatus.OK);
    }

    @PutMapping("/{contentId}/license/{licenseId}/add")
    ResponseEntity<Object> enrollLicenseToContent(@PathVariable Long contentId, @PathVariable Long licenseId) {
        return new ResponseEntity<>(contentService.enrollLicenseToContent(contentId, licenseId), HttpStatus.OK);
    }
    @DeleteMapping("/{contentId}/license/{licenseId}/delete")
    ResponseEntity<Object> deleteLicenseFromContent(@PathVariable Long contentId, @PathVariable Long licenseId) {
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







