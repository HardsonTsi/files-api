package com.hardtech.filesapi.controller;


import com.hardtech.filesapi.entity.FileDetails;
import com.hardtech.filesapi.service.FileDetailsServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/filesapi")
@AllArgsConstructor
public class FileDetailsControllers {

    private final FileDetailsServices fileDetailsServices;


    @GetMapping("/all")
    List<FileDetails> getAllFiles() {
        return fileDetailsServices.getAllFiles();
    }

    @PostMapping("/upload")
    String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return fileDetailsServices.upload(file).getId();
    }

    @PutMapping("/update/{oldId}")
    String updateFile(@PathVariable(name = "oldId") String id,
                      @RequestParam("newFile") MultipartFile file) throws IOException {
        return fileDetailsServices.update(id, file).getId();
    }

    @DeleteMapping("/delete/{id}")
    boolean deleteFileDetails(@PathVariable(name = "id") String id) {
        return fileDetailsServices.deleteFileDetails(id);
    }

    @GetMapping("/download/{id}")
    ResponseEntity<?> download(@PathVariable String id, HttpServletRequest request) {
        FileDetails f = fileDetailsServices.getFileDetails(id);
        try {
            String contentType = request.getServletContext().getMimeType(f.getLocalURL());
            if (contentType == null) contentType = "application/octet-stream";
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + f.getFilename() + "\"")
                    .body(fileDetailsServices.download(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
