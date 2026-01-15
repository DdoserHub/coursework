package com.example.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.secret_key}")
    private String secretKeyHex;

    @Value("${security.jwt.access_token_expiration}")
    private long accessTokenExpirationMs;

    private SecretKey signingKey() {
        byte[] keyBytes = hexToBytes(secretKeyHex);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String subjectEmail) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .subject(subjectEmail)
                .issuedAt(new Date(now))
                .expiration(new Date(now + accessTokenExpirationMs))
                .signWith(signingKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Date exp = claims.getExpiration();
            return exp != null && exp.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private static byte[] hexToBytes(String hex) {
        if (hex == null) throw new IllegalArgumentException("JWT secret key is null");
        String s = hex.trim();
        if (s.length() % 2 != 0) throw new IllegalArgumentException("Hex string must have even length");

        byte[] out = new byte[s.length() / 2];
        for (int i = 0; i < s.length(); i += 2) {
            int hi = Character.digit(s.charAt(i), 16);
            int lo = Character.digit(s.charAt(i + 1), 16);
            if (hi == -1 || lo == -1) throw new IllegalArgumentException("Invalid hex in JWT secret key");
            out[i / 2] = (byte) ((hi << 4) + lo);
        }
        return out;
    }
}
