package com.atlei.controller;

import com.atlei.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * ファイルアップロードコントローラー
 */
@Slf4j
@RestController
public class UploadController {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

    /**
     * プロフィール画像等のファイルアップロード処理
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return Result.error("アップロードファイルが空です");
        }

        String originalFilename = file.getOriginalFilename();
        String suffix = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String newFileName = UUID.randomUUID() + suffix;
        file.transferTo(new File(dir, newFileName));
        log.info("ファイルをアップロードしました: fileName={}, size={} bytes", newFileName, file.getSize());

        return Result.success("/uploads/" + newFileName);
    }
}