package app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    //@Value("${jwt.expiration}")
    private Long expiration = 60000000L;
    private final Key secret;

    public JwtUtil(@Value("${jwt.secretString}") String secretString) {
        secret = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String userName, Collection<? extends GrantedAuthority> authorities) {
        return Jwts.builder()
                .setSubject(userName)
                .claim("roles", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secret)
                .compact();
    }

    public boolean isValidToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token);

        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    public String getUserName(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsTFunction.apply(claims);
    }
}
