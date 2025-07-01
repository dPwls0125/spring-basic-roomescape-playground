package roomescape.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import roomescape.member.MemberService;

@Component
public class AdminRoleCheckInterceptor implements HandlerInterceptor {

    private final MemberService memberService;

    public AdminRoleCheckInterceptor(final MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        LoginMember member = memberService.parseTokenAndGetMemberInfo(request.getCookies());
        if (member == null || !member.getRole().equals("ADMIN")) {
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
