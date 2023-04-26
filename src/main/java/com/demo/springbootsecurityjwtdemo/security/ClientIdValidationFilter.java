package com.demo.springbootsecurityjwtdemo.security;

import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.demo.springbootsecurityjwtdemo.api.AuthenticationApi.CLIENT_ID_HEADER;
import static com.demo.springbootsecurityjwtdemo.api.dto.ErrorCode.CLIENT_NOT_VALID;

@Component
public class ClientIdValidationFilter extends OncePerRequestFilter {

    private static final Set<String> NO_CLIENT_ID_URLs = new HashSet<>(Arrays.asList(
            "/swagger-ui",
            "/api-docs",
            "/webjars"));

    @Value("#{'${app.clientIds}'.split(',')}")
    private List<String> clientIds;

    private final HandlerExceptionResolver exceptionResolver;

    public ClientIdValidationFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        Optional<String> match = NO_CLIENT_ID_URLs.stream().filter(requestUri::contains).findFirst();
        if (match.isEmpty()) {
            String clientId = request.getHeader(CLIENT_ID_HEADER);
            if (StringUtils.isBlank(clientId) || !this.clientIds.contains(clientId)) {
                ApplicationException e = new ApplicationException(CLIENT_NOT_VALID);
                this.exceptionResolver.resolveException(request, response, null, e);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
