package roomescape.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import roomescape.member.AuthenticatedMemberResponse;

import java.util.Base64;

@Component
public class TokenParser {

    @Value("${roomescape.auth.jwt.secret}")
    private String secretKey;

    public AuthenticatedMemberResponse paseMemberInfo(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        String name = claims.get("name", String.class);
        String role = claims.get("role", String.class);
        long id = Long.parseLong(claims.getSubject());

        return new AuthenticatedMemberResponse(id, name, role);


    }

}
