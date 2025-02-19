package com.example.newspeed.auth.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "SNS8";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // 사용자명 반환
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static String getExpirationTime(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = claims.getExpiration(); // 토큰 만료 시간 가져오기
            long now = System.currentTimeMillis();
            return String.valueOf(expiration.getTime() - now);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}