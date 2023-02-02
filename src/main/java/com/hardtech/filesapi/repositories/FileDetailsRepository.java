package com.hardtech.fileapi.repositories;


import com.hardtech.fileapi.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDetailsRepository extends JpaRepository<FileDetails, String> {
    FileDetails findByIdEquals(String id);

}