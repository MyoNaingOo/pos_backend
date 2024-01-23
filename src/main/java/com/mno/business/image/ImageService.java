package com.mno.business.image;

import com.mno.business.image.ImageData;
import com.mno.business.image.ImageRepo;
import com.mno.shop.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;

    public String  uploadfile(MultipartFile file) throws IOException {




            ImageData imageData = imageRepo.save(
                    ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes())).build());

        return imageData.toString();




//        file.transferTo(new File("C:\\Users\\PC\\Pictures\\"+file.getOriginalFilename()));
    }

    public byte[] downloadImage(String filename){
        Optional<ImageData> dbImageData= imageRepo.findByName(filename);

        return ImageUtil.decompressImage(dbImageData.get().getImageData());
    }

    public void deleteByName(String filename){
        imageRepo.deleteByName(filename);
    }



}
