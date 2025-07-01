package roomescape.member;

public class AuthenticatedMemberResponse {

    private Long id;
    private String name;
    private String role;

    public AuthenticatedMemberResponse(final Long id, final String name, final String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
