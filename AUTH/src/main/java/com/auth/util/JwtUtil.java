package com.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    public static final String JWT_SECRET = "A1B2C3DE4F5G6H7I8J90KLMNOPQRSTUVWXYZTRILOKCHANDTHAKURMEWURKTESTINGLIBRARYDOTCOMNEWUSERVALUSEWITHNEWSIGNUPUSERNAMEEMAILPASSWORDANDFIRSTNAMELASTNAMETOKEJWTSPRINGBOOTREATJSNODEJSJAVAJAVASCRIPTGOPYTHONMLAIDLDSANDSOON";

    public String generateToken(String username) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", "trilok@gmail.com");

        return Jwts.builder().subject(username)
                .issuer(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*5))
                .claims(claims)
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
       byte[] bytes = Base64.getDecoder().decode(JWT_SECRET);
       return Keys.hmacShaKeyFor(bytes);

    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }
}
