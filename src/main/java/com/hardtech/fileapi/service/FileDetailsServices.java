package com.hardtech.fileapi.service;



import com.hardtech.fileapi.entity.FileDetails;
import com.hardtech.fileapi.repositories.FileDetailsRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileDetailsServices {

            private final FileDetailsRepository fileDetailsRepository;


    public FileDetails upload(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDetails fileDetails = new FileDetails();
        String extension = "." + FilenameUtils.getExtension(fileName);
        fileDetails.setId(UUID.randomUUID().toString());
        String uniqueName = fileDetails.getId() + extension;

        try {

            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence" + fileName);
            }

            fileDetails.setContentType(file.getContentType());

            String path = new File(System.getProperty("user.home") + uniqueName).getAbsolutePath();

            file.transferTo(new File(path));

            fileDetails.setFilename(uniqueName);
            fileDetails.setExtension(extension);

            fileDetails.setLocalURL(path);

            String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/filesServer/")
                    .path("/download/")
                    .path(fileDetails.getId())
                    .toUriString();
            fileDetails.setUrl(url);

            return fileDetailsRepository.save(fileDetails);

        } catch (Exception e) {
            throw new RuntimeException("Could't save file" + fileName);
        }
    }

    public byte[] download(String id) throws IOException {
        FileDetails fileDetails = fileDetailsRepository.findByIdEquals(id);
        File file = new File(fileDetails.getLocalURL());
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    public FileDetails update(String id, MultipartFile file) throws IOException {
        FileDetails fileDetails = fileDetailsRepository.findByIdEquals(id);
        File file1 = new File(fileDetails.getLocalURL());
        Path path = Paths.get(file1.toURI());

        file.transferTo(path);

        return fileDetails;
    }

    public boolean deleteFileDetails(String id) {
        FileDetails fileDetails = fileDetailsRepository.findByIdEquals(id);
        File file1 = new File(fileDetails.getLocalURL());
        fileDetailsRepository.deleteById(id);
        return file1.delete();
    }

    public List<FileDetails> getAllFiles() {
        return fileDetailsRepository.findAll();
    }

    public FileDetails getFileDetails(String id) {
        return fileDetailsRepository.findByIdEquals(id);
    }

}
