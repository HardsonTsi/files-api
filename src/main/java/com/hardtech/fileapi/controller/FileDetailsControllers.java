package com.hardtech.fileapi.controller;


import com.hardtech.fileapi.entity.FileDetails;
import com.hardtech.fileapi.service.FileDetailsServices;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping(value = "/download/{id}", produces = MediaType.ALL_VALUE)
    byte[] download(@PathVariable String id) throws IOException {

        return fileDetailsServices.download(id);

    }

}
