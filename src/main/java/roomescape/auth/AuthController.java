package roomescape.auth;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.member.MemberResponse;
import roomescape.token.TokenResponse;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response) {

        ResponseCookie responseCookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

    @GetMapping("/login/check")
    public ResponseEntity<MemberResponse> loginCheck(LoginMember loginMember) {
        if (loginMember == null) {
            return ResponseEntity.status(401).build();
        }
        MemberResponse response = new MemberResponse(loginMember.getId(), loginMember.getName(), loginMember.getEmail());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {

        TokenResponse token = authService.createToken(loginRequest.email(), loginRequest.password());
        ResponseCookie cookie = ResponseCookie.from("token", token.getAccessToken())
                .httpOnly(true)
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
