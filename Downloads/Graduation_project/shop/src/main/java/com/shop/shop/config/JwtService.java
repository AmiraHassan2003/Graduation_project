package com.shop.shop.config;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private static final String SECRET_KEY = "612b342d41696a2c732863506256615a2d726c2625464a343a6f503a5d";
    /* extract data from token */
    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private static Claims extractAllClaims(String token) {
        return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }
    private static Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
    }
    /* generate token  with userdetails*/
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    /* generate token  with userdetails and claims */
     public String generateToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails
    ) {
     return Jwts
     .builder()
     .setClaims(extraClaims)
     .setSubject(userDetails.getUsername())
     .setIssuedAt(new Date(System.currentTimeMillis()))
     .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 24))
     .signWith(getSignInKey(),SignatureAlgorithm.HS256)
     .compact();
    }
     /*token  is valid */
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username =extractUsername(token);
        return(username.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
}
