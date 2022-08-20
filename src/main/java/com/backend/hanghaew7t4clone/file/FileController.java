package com.backend.hanghaew7t4clone.file;

import com.backend.hanghaew7t4clone.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FileController {
   private final FileService fileService;

    @PostMapping("/auth/photos")
    public ResponseEntity<?> upload(MultipartFile[] photoList) throws Exception {
       List<String> imageUrlList=fileService.getImgUrlList(photoList);
       return new ResponseEntity<>(Message.success(imageUrlList), HttpStatus.OK);
    }

}