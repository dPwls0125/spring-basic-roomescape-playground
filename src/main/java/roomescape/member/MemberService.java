package roomescape.member;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;
import roomescape.auth.LoginMember;
import roomescape.token.TokenParser;
import roomescape.token.TokenProvider;
import roomescape.token.TokenResponse;

@Service
public class MemberService {
    private final MemberDao memberDao;
    private final TokenProvider tokenProvider;
    private final TokenParser tokenParser;

    public MemberService(final MemberDao memberDao, final TokenProvider tokenProvider, final TokenParser tokenParser) {
        this.memberDao = memberDao;
        this.tokenProvider = tokenProvider;
        this.tokenParser = tokenParser;
    }

    public MemberResponse createMember(MemberRequest memberRequest) {
        Member member = memberDao.save(new Member(memberRequest.getName(), memberRequest.getEmail(), memberRequest.getPassword(), "USER"));
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

    public TokenResponse getAccessToken(String email, String password) {
        Member member = getMember(email, password);
        String token = tokenProvider.createAccessToken(member);
        return new TokenResponse(token);
    }

    public LoginMember parseTokenAndGetMemberInfo(Cookie[] cookies) {

        String token = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token"))
                token = cookie.getValue();
        }

        if (token.isEmpty()) throw new IllegalArgumentException("로그인하지 않은 사용자입니다.");

        return tokenParser.paseMemberInfo(token);
    }

    private Member getMember(String email, String password) {
        Member member = memberDao.findByEmailAndPassword(email, password);
        if (member == null) throw new IllegalArgumentException("등록되지 않은 사용자입니다.");
        return member;
    }
}
