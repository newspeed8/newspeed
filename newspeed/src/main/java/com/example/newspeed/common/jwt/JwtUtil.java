package com.example.newspeed.common.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationMs;//1일 유효기간

    //    public String generateJwtToken(String email) {
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
    public String generateJwtToken(String email, Long userId) {
        Key signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //    public Long getUserIdFromJwtToken(String token) {
//        // Bearer 접두사 있으면 제거
//        if(token.startsWith("Bearer ")) {
//            token = token.substring(7);
//        }
//        String userIdStr = Jwts.parser()
//                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
//                .parseClaimsJws(token)
//                .getBody()
//                .get("userId", String.class);
//        return Long.parseLong(userIdStr);
//    }
    public Long getUserIdFromJwtToken(String token) {
        // Bearer 접두사 있으면 제거
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Claims claims = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .parseClaimsJws(token)
                .getBody();

        String userIdStr = claims.get("userId", String.class);
        System.out.println("추출된 userId 클레임: " + userIdStr);

        if (userIdStr == null) {
            throw new IllegalArgumentException("jwt 톹큰에 userId 클레임이 없어요");
        }
        return Long.parseLong(userIdStr);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.err.println("잘못된 JWt : " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("만료된 JWT : " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("지원되지 않는 JWT : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT가 빈 값 입니다: " + e.getMessage());
        }
        return false;
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization"); // Authorization 헤더에서 추출
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // "Bearer " 부분을 제외하고 토큰만 반환
        }
        return null; // 토큰이 없으면 null 반환
    }
}
