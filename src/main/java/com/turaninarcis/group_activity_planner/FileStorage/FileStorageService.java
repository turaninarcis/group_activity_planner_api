package com.turaninarcis.group_activity_planner.FileStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    
    @Value("${upload.dir}")
    private String uploadDir;
    
    public String save(MultipartFile file){
        try{
            Path uploadPath = Paths.get("").toAbsolutePath().resolve(uploadDir);
            
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            String thumbnailName = "thumbnail_"+ fileName;
            System.out.println("Original name"+file.getOriginalFilename());

            Files.createDirectories(uploadPath);
            System.out.println("11111111111");

            Path OriginalFileDestination = uploadPath.resolve(fileName);
            Path ThumbnailFileDestination = uploadPath.resolve(thumbnailName);

            Files.createDirectories(OriginalFileDestination.getParent());
            System.out.println("222222222222222");

            file.transferTo(OriginalFileDestination.toFile());
            System.out.println("33333333333333");
            Thumbnails.of(OriginalFileDestination.toFile())
                       .size(300, 300)
                       .toFile(ThumbnailFileDestination.toFile());
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to store image", e);
        }
    }
}
