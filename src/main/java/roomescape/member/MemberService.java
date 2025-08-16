package roomescape.member;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberDao memberDao;

    public MemberService(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberResponse createMember(MemberRequest memberRequest) {
        Member member = memberDao.save(new Member(memberRequest.name(), memberRequest.email(), memberRequest.password(), Role.USER));
        return new MemberResponse(member.getId(), member.getName(), member.getEmail());
    }

}
