package com.example.saat.controllers;

import com.example.saat.models.VideoOperationProcess;
import com.example.saat.services.VideoOperationProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/process")
public class VideoOperationProcessController {
    private final VideoOperationProcessService videoOperationProcessService;

    @Autowired
    public VideoOperationProcessController(VideoOperationProcessService videoOperationProcessService) {
        this.videoOperationProcessService = videoOperationProcessService;
    }

    @GetMapping
    ResponseEntity<Object> getProcesses(){
        return new ResponseEntity<>(videoOperationProcessService.getProcesses(), HttpStatus.OK);
    }
    @PostMapping
    ResponseEntity<Object> createProcess(@RequestBody VideoOperationProcess process) {
        return new ResponseEntity<>(videoOperationProcessService.createProcess(process), HttpStatus.OK);
    }
}
