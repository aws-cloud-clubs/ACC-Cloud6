package com.cloud6.chat.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String getRoomIdFromToken(String token) {
        return getAllClaimsFromToken(token).get("roomId", String.class);
    }

    public String getUserIdFromToken(String token) {
        return getAllClaimsFromToken(token).get("userId", String.class);
    }

    public String getNicknameFromToken(String token) {
        return getAllClaimsFromToken(token).get("nickname", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            return false;
        }
    }

    public void validateTokenInChat(String token) {
        if (!validateToken(token)) {
            throw new RuntimeException("Invalid JWT Token");
        }
    }
}
