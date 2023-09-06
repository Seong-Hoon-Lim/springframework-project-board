package com.example.springframeworkprojectboard.config.filter;

import com.example.springframeworkprojectboard.service.TokenProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
public class JwtAuthenticationFilter implements Filter {

    private TokenProvider tokenProvider;

    public JwtAuthenticationFilter() {}

    @Autowired
    public JwtAuthenticationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        //헤더에서 "Bearer " 를 제거
        String token = header.substring(7);
        try {
            String user = tokenProvider.validateToken(token);
            log.info("Authenticated user: {}", user);
        } catch (Exception e) {
            log.error("Token 검증 실패: {}", e.getMessage());
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증되지 않은 token");
            return;
        }
        chain.doFilter(request, response);
    }
}
