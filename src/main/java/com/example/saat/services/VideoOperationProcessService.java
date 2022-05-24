package com.example.saat.services;

import com.example.saat.models.VideoOperationProcess;
import com.example.saat.repository.VideoOperationProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoOperationProcessService {
    private final VideoOperationProcessRepository videoOperationProcessRepository;

    @Autowired
    public VideoOperationProcessService(VideoOperationProcessRepository videoOperationProcessRepository) {
        this.videoOperationProcessRepository = videoOperationProcessRepository;
    }
    public List<VideoOperationProcess> getProcesses() {
        return videoOperationProcessRepository.findAll();
    }
    public String createProcess(VideoOperationProcess videoOperationProcess) {
        videoOperationProcessRepository.save(videoOperationProcess);
        return ("Process has been created");
    }

}
