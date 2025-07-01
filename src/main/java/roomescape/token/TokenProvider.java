package roomescape.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import roomescape.member.Member;

import java.util.Base64;
import java.util.Date;


@Component
public class TokenProvider {

    @Value("${roomescape.auth.jwt.secret}")
    private String secretKey;

    @Value("${roomescape.auth.jwt.token.expire-length}")
    private long expire;

    public String createAccessToken(Member member) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expire);
        return Jwts.builder()
                .setSubject(Long.toString(member.getId()))
                .claim("name", member.getName())
                .claim("role", member.getRole())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(
                        Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }
}
