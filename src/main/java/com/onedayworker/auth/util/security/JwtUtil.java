package com.onedayworker.auth.util.security;
import com.onedayworker.auth.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {


    public String generateToken(String email, Long employeeId, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("employee_id", employeeId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Constants.accessTokenValidity)) // 1 hour
                .signWith(Keys.hmacShaKeyFor(Constants.SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Constants.refreshTokenValidity))
                .signWith(Keys.hmacShaKeyFor(Constants.SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }
    public Long extractEmployeeId(String token) {
        return getClaims(token).get("employee_id", Long.class);
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(Constants.SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
