package com.example.kakaopay.filter;

import com.example.kakaopay.common.RequestContextUtil;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Order(0)
public class RequestInitFilter extends OncePerRequestFilter {
    private boolean isInitialized = false;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String requestId = UUID.randomUUID().toString();
        RequestContextUtil.setRequestId(requestId);
        ThreadContext.put("requestId", requestId);

        chain.doFilter(request, response);
    }
}
