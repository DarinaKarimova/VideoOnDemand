package com.example.saat.jwt;

import com.example.saat.models.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hours
    @Value("${app.jwt.secret}")
    private String secretKey;

    public String generateAccessToken(User user){
        return Jwts.builder()
                .setSubject(user.getId() + "," + user.getUsername())
                .setIssuer("CodeJava")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateAccessToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expired){
            LOGGER.error("Jwt expired", expired);
        } catch (IllegalArgumentException expired){
            LOGGER.error("Token is null, empty or has only whitespace", expired);
        } catch (MalformedJwtException expired){
            LOGGER.error("Jwt is invalid", expired);
        } catch (UnsupportedJwtException expired){
            LOGGER.error("Jwt is not supported", expired);
        } catch (SignatureException expired){
            LOGGER.error("Signature validation failed", expired);
        }
        return false;
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
