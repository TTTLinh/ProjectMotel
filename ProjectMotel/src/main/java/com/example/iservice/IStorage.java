package com.example.iservice;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface IStorage {
    //định nghiã phương thức để taoj ra file lưu trữ dựa vào id đc truyền vô
    String getStoredFilename(MultipartFile file, String id);

    // lưu nội dung của file từ multipartfile
    void store(MultipartFile file, String storedFilename);

    //Nạp nôi dung của file dưới dạng resource
    Resource loadAsResource(String filename);

    Path load(String filename);

    //xóa file khi k cần
    void  delete(String storedFilename) throws IOException;

    //khới tạo các thư mục
    void init();
}
