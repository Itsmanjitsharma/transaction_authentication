package com.app.authentication.transaction_authentication.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.app.authentication.transaction_authentication.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;

@Component
@Slf4j
public class JwtUtil {
     private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  //@Value("${transaction.app.jwtSecret}")
  private String jwtSecret = "===========================================authorizationapp============================================";

  
  @Value("${transaction.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @SuppressWarnings("unchecked")
 public Set<String> extractRoles(String token) {
    List<String> rolesList = extractAllClaims(token).get("roles", List.class);
    return rolesList != null ? new HashSet<>(rolesList) : Collections.emptySet();
}

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }

 

  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
        .parseClaimsJws(token).getBody().getSubject();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean validateJwtToken(String authToken) {
    logger.info("request to vaidate token");
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
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


  public String generateToken(User user) {
    logger.info("request for logger generation");
  return Jwts.builder()
        .setSubject((user.getUsername()))
        .claim("roles", user.getAuthorities().stream()
                    .map(role->role.getAuthority())
                    .collect(Collectors.toSet()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }
}
