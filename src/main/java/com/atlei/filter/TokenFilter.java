package com.atlei.filter;

import com.atlei.utils.CurrentHolder;
import com.atlei.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * JWTトークン検証用サーブレットフィルター
 * ※ Spring MVCのTokenInterceptorへ移行したため、現在は@WebFilterを無効化中
 */
@Slf4j
//@WebFilter("/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 1. リクエストURIを取得
        String requestURI = req.getRequestURI();

        // 2. 認証不要なリクエスト（ログインAPIなど）の場合は検証をスキップして通過させる
        if (requestURI.equals("/login")) {
            log.info("ログインリクエストのため、検証をスキップします");
            chain.doFilter(request, response);
            return;
        }

        // 3. リクエストヘッダーからトークンを取得し、存在チェックを実施（未設定の場合は401エラーを返却）
        String token = req.getHeader("token");
        if (token == null || token.isEmpty()) {
            log.info("トークンが空のため、401ステータスコードを返却します");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 4. トークンの有効性を検証し、操作担当者IDをThreadLocalに格納（検証失敗時は401エラーを返却）
        try {
            Claims claims = JwtUtils.parseToken(token);
            Object id = claims.get("id");
            Integer empId = Integer.valueOf(id.toString());
            CurrentHolder.setCurrentId(empId);
            log.info("ログイン中の従業員ID: {} をThreadLocalに格納しました", empId);

        } catch (Exception e) {
            log.info("無効なトークンのため、401ステータスコードを返却します");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 5. 検証成功のため、リクエストを次のフィルターまたはサーブレットへ渡す
        log.info("トークン検証成功、リクエストを許可します");
        chain.doFilter(req, resp);

        // 6. 処理完了後にThreadLocalの値をクリア（メモリリーク防止）
        CurrentHolder.remove();
    }
}