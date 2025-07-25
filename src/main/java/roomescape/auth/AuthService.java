package roomescape.auth;

import org.springframework.stereotype.Service;
import roomescape.member.Member;
import roomescape.member.MemberDao;
import roomescape.token.TokenParser;
import roomescape.token.TokenProvider;
import roomescape.token.TokenResponse;

@Service
public class AuthService {
    private final MemberDao memberDao;
    private final TokenProvider tokenProvider;
    private final TokenParser tokenParser;

    public AuthService(MemberDao memberDao, TokenProvider tokenProvider, TokenParser tokenParser) {
        this.memberDao = memberDao;
        this.tokenProvider = tokenProvider;
        this.tokenParser = tokenParser;
    }

    public TokenResponse createToken(String email, String password) {
        Member member = memberDao.findByEmailAndPassword(email, password);
        if (member == null) {
            throw new IllegalArgumentException("등록 되지 않은 사용자입니다.");
        }
        String token = tokenProvider.createAccessToken(member);
        return new TokenResponse(token);
    }

    public LoginMember parseMemberInfo(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("로그인 하지 않은 사용자입니다.");
        }
        return tokenParser.parseMemberInfo(token);
    }
}
