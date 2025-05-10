package com.turaninarcis.group_activity_planner.FileStorage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/chat/upload")
public class FileStorageController {
    private final FileStorageService fileStorageService;

    public FileStorageController(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String,String>> uploadImage(@RequestParam("image") MultipartFile image) {
        String url = fileStorageService.save(image);
        return ResponseEntity.ok(Map.of("imageUrl",url));
    }
    
}
