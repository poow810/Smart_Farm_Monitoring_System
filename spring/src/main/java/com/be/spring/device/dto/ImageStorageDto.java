package com.be.spring.device.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageStorageDto {

    private MultipartFile image;
}
