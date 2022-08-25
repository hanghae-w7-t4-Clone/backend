package com.backend.hanghaew7t4clone.domain.card;

import com.backend.hanghaew7t4clone.domain.card.FileService;
import com.backend.hanghaew7t4clone.global.shared.Message;
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
       List<String> imgUrlList=fileService.getImgUrlList(photoList);
       return new ResponseEntity<>(Message.success(imgUrlList), HttpStatus.OK);
    }

}