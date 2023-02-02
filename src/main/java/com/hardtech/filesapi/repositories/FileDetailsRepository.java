package com.hardtech.filesapi.repositories;


import com.hardtech.filesapi.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDetailsRepository extends JpaRepository<FileDetails, String> {
    FileDetails findByIdEquals(String id);

}