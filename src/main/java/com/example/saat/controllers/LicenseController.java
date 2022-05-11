package com.example.saat.controllers;

import com.example.saat.models.License;
import com.example.saat.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/license")
public class LicenseController {
    private final LicenseService licenseService;
    @Autowired
    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }
//    @GetMapping
//    public List<License> getLicenses(){
//        return licenseService.getLicenses();
//    }
    @GetMapping
    ResponseEntity<List<License>> getLicenses() {
        return new ResponseEntity<>(licenseService.getLicenses(), HttpStatus.OK);
    }
    @GetMapping ("{licenseId}")
    ResponseEntity<Object> getLicense(@PathVariable("licenseId") Long licenseId) {
        if (!licenseService.licenseExists(licenseId)) {
            return ResponseEntity.badRequest().body("License with id " + licenseId + " does not exist");
        }
        return new ResponseEntity<>(licenseService.getLicense(licenseId), HttpStatus.OK);
    }
//    @PostMapping
//    public void registerNewLicense(@RequestBody License license) {
//       licenseService.addNewLicense(license);
//    }
    @PostMapping
    ResponseEntity<Object> registerNewLicense(@RequestBody License license) {
       return new ResponseEntity<>(licenseService.addNewLicense(license), HttpStatus.OK);
    }

//    @DeleteMapping(path = "{licenseId}")
//    public void DeleteLicense(@PathVariable("licenseId") Long licenseId){
//        licenseService.deleteLicense(licenseId);
//    }
    @DeleteMapping("{licenseId}")
    ResponseEntity<Object> deleteLicense(@PathVariable("licenseId") Long licenseId){
        if(!licenseService.licenseExists(licenseId)){
            return ResponseEntity.badRequest().body("License with id " + licenseId + " cannot be deleted, because it does not exist");
        }
        return new ResponseEntity<>(licenseService.deleteLicense(licenseId), HttpStatus.OK);
    }
//    @PutMapping("/{licenseId}")
//    public void updateLicense(@PathVariable(value = "licenseId")Long licenseId,
//                              @RequestBody License licenseDetails){
//        licenseService.updateLicense(licenseId, licenseDetails);
//    }
    @PutMapping("/{licenseId}")
    ResponseEntity<Object> updateLicense(@PathVariable(value = "licenseId")Long licenseId, @RequestBody License licenseDetails){
        if(!licenseService.licenseExists(licenseId)){
            return ResponseEntity.badRequest().body("License with id " + licenseId + " cannot be updated, because it does not exist");
        }
        return new ResponseEntity<>(licenseService.updateLicense(licenseId, licenseDetails), HttpStatus.OK);
    }
}
