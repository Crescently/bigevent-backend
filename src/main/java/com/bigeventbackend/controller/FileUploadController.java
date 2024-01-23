package com.bigeventbackend.controller;

import com.bigeventbackend.common.Result;
import com.bigeventbackend.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        // 文件存储到本地目录
        String filename = multipartFile.getOriginalFilename();
        // 保证文件名唯一 ——使用UUID
        assert filename != null;
        filename = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
//        multipartFile.transferTo(new File("E:\\LocalFiles\\WorkSpace\\Java\\bigevent-backend\\files\\" + filename));
        String fileUrl = AliOssUtil.uploadFile(filename, multipartFile.getInputStream());
        return Result.success(fileUrl);
    }
}
