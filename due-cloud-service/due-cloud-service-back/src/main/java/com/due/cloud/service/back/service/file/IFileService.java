package com.due.cloud.service.back.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    Long uploadFile(MultipartFile multipartFile);
}
