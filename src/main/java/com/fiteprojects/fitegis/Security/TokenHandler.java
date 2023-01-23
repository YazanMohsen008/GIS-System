package com.fiteprojects.fitegis.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

@Component
public class TokenHandler {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
    private static final String SECRET = "secret";


    public String generateToken(String username) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    Claims parseToken(String token) throws ExpiredJwtException{
        try {
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        }
        catch (ExpiredJwtException e){
            e.printStackTrace();
            throw e;
        }
    }

    public String getUsername(String token) throws ExpiredJwtException{
        return parseToken(token).getSubject();
    }

    Date getExpirationDate(String token) throws ExpiredJwtException {
        return parseToken(token).getExpiration();
    }

    public boolean isTokenExpired(String token) throws ExpiredJwtException{
        return new Date().after(getExpirationDate(token));
    }


}
