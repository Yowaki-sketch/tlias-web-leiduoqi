package com.atlei.exception;

import com.atlei.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * データベースの一意制約違反（重複登録）のハンドリング
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("データ重複エラーが発生しました: {}", e.getMessage());
        String message = e.getMessage();
        if (message != null && message.contains("Duplicate entry")) {
            int index = message.indexOf("Duplicate entry");
            String errMsg = message.substring(index);
            String[] arr = errMsg.split(" ");
            if (arr.length > 2) {
                return Result.error(arr[2] + " は既に登録されています。");
            }
        }
        return Result.error("入力されたデータは既に登録されています。");
    }

    /**
     * その他の予期せぬシステム例外のハンドリング
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("システムエラーが発生しました: ", e);
        return Result.error("システムエラーが発生しました。管理者に連絡してください。");
    }
}
