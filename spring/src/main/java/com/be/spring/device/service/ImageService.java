package com.be.spring.device.service;


import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final Storage storage;
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public String uploadImage(String base64Image, String imageName) {
        BlobId blobId = BlobId.of(bucketName, imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();

        byte[] bytes = Base64.getDecoder().decode(base64Image);

        storage.create(blobInfo, bytes);


        return "https://storage.googleapis.com/" + bucketName + "/" + imageName;
    }
}
