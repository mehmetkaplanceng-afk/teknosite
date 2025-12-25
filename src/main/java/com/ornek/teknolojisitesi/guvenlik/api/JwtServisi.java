package com.ornek.teknolojisitesi.guvenlik.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtServisi {

    private final SecretKey key;
    private final long expirationMinutes;

    public JwtServisi(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expiration-minutes:1440}") long expirationMinutes) {

        byte[] bytes;
        try {
            // Secret base64 ise decode etmeyi dener
            bytes = java.util.Base64.getDecoder().decode(secret);
        } catch (Exception e) {
            // Değilse raw string bytes kullanır
            bytes = secret.getBytes(StandardCharsets.UTF_8);
        }

        this.key = Keys.hmacShaKeyFor(bytes);
        this.expirationMinutes = expirationMinutes;
    }

    public String tokenUret(UserDetails user) {
        Instant now = Instant.now();

        List<String> roller = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", roller)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String kullaniciAdiCek(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();   // 0.12.x: getBody() deprecated, getPayload() kullanılır

        return claims.getSubject();
    }
}
