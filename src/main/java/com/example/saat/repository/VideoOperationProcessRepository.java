package com.example.saat.repository;

import com.example.saat.models.VideoOperationProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoOperationProcessRepository extends JpaRepository<VideoOperationProcess, Long> {
}
