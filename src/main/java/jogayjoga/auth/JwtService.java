package jogayjoga.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

    @Value("${ingressify.jwt.secret-key}")
    private String secretKey;

    @Value("${ingressify.jwt.issuer}")
    private String issuer;

    @Value("${ingressify.jwt.duration}")
    private long duration = 1l;

    private SecretKey key;
    private JwtParser parser;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.parser = Jwts.parser().verifyWith(key).build();
    }

    public String create(String id, String name, String role) {
        String jwt = Jwts.builder()
            .header()
            .and()
            .id(id)
            .issuer(issuer)
            .subject(name)
            .signWith(key)
            .claim("role", role)
            .notBefore(new Date())
            .expiration(new Date(new Date().getTime() + duration))
            .compact();
        return jwt;
    }

    public Token getToken(String token) {
        final Claims claims = resolveClaims(token);
        return Token.builder()
            .id(claims.getId())
            .role(claims.get("role", String.class))
            .build();
    }

    private Claims resolveClaims(String token) {
        if (token == null) throw new io.jsonwebtoken.MalformedJwtException("token is null");
        return validateClaims(parser.parseSignedClaims(token).getPayload());
    }

    private Claims validateClaims(Claims claims) throws ExpiredJwtException {
        if (claims.getExpiration().before(new Date())) throw new ExpiredJwtException(null, claims, issuer);
        if (claims.getNotBefore().after(new Date())) throw new ExpiredJwtException(null, claims, issuer);
        return claims;
    }

}