package com.backend.security.jwt;

import com.backend.security.services.AuthenticationFacade;
import com.backend.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.accessToken.duration}")
    private int jwtExpirationMs;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    public String generateJwtToken() {
        UserDetailsImpl userDetail = (UserDetailsImpl) authenticationFacade.getAuthentication();

        Claims claims = Jwts.claims();
        claims.put("userId", userDetail.getId());
        claims.put("username", userDetail.getUsername());
        claims.put("roles", userDetail.getAuthorities());
        claims.setSubject(userDetail.getUsername());
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String getInGameFromJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
