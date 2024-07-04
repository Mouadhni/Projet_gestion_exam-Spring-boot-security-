package org.example.miniprojet.service;

import org.example.miniprojet.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
@Service
public class JwtService {
    private  final String SECRET_KEY="ab691b9a9870df6adf28abb1194fd2d0ea9ad039c663a7c5b6fc7e427cfb1b2e";
    public String extractUsername(String token){
        return  extractClaim(token ,Claims::getSubject);
    }
    public boolean isValid(String token, UserDetails user){
        String username=extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return  extractClaim(token,Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        Claims claims=extractAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return  Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String generateToken(User user){
        String token= Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+14*60*60*1000))
                .signWith(getSigninKey())
                .compact();
        return token;
    }
    private SecretKey getSigninKey(){
        byte[] keyBytes= Decoders.BASE64URL.decode(SECRET_KEY);
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }
}
