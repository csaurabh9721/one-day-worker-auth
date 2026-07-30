package com.onedayworker.auth.util.security;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        // Swagger
        if (uri.startsWith("/swagger-ui")
                || uri.startsWith("/v3/api-docs")
                || uri.startsWith("/testApi")) {
            return true;
        }

        // Public authentication APIs
        if ("POST".equals(method)) {

            return uri.equals("/authApi/v1/auth/login")
                    || uri.equals("/authApi/v1/auth/registerCustomer")
                    || uri.equals("/authApi/v1/auth/registerWorker")
                    || uri.equals("/authApi/v1/auth/refresh")
                    || uri.equals("/authApi/v1/auth/forgot-password")
                    || uri.equals("/authApi/v1/otp/send")
                    || uri.equals("/authApi/v1/otp/verify");
        }

        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            if (jwtUtil.isTokenValid(token) &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    Long employeeId = jwtUtil.extractEmployeeId(token);
                    String email = jwtUtil.extractEmail(token);
                    String role = jwtUtil.extractRole(token);

                    List<GrantedAuthority> authorities =
                            List.of(new SimpleGrantedAuthority("ROLE_" + role));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    employeeId,
                                    null,
                                    authorities
                            );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);

                } catch (Exception e) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}

