package com.mno.business.image;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;


    @PostMapping(value = "add",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void ploadimage(@RequestParam("file")MultipartFile[] files) throws IOException {

        for(MultipartFile file : files) {
            String uploadimage = imageService.uploadfile(file);
        }

    }

    @GetMapping("{filename}")
    public ResponseEntity<?> downloadimage(@PathVariable("filename") String file) throws IOException {
        try{
            byte[] imageData = imageService.downloadImage(file);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(imageData);
        }catch (Exception ignored){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("image is not right");
        }
    }

    @DeleteMapping("delete/{filename}")
    public void deleteByName(@PathVariable("filename") String filename){
        imageService.deleteByName(filename);
    }


}
