package com.atlei.interceptor;

import com.atlei.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWTトークン検証用インターセプター
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. リクエストヘッダーからトークンを取得し、存在チェックを実施（未設定の場合は401エラーを返却）
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            log.info("トークンが空のため、401ステータスコードを返却します");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 2. トークンの有効性を検証（検証失敗時は401エラーを返却）
        try {
            JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.info("無効なトークンのため、401ステータスコードを返却します");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 3. 検証成功のため、リクエストを許可
        log.info("トークン検証成功、リクエストを許可します");
        return true;
    }
}