package roomescape.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import roomescape.auth.AdminRoleCheckInterceptor;
import roomescape.auth.LoginMemberArgumentResolver;
import roomescape.auth.TokenInterceptor;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginMemberArgumentResolver loginMemberArgumentResolver;
    private final AdminRoleCheckInterceptor adminRoleCheckInterceptor;
    private final TokenInterceptor tokenInterceptor;

    public WebConfig(LoginMemberArgumentResolver loginMemberArgumentResolver, AdminRoleCheckInterceptor adminRoleCheckInterceptor, TokenInterceptor tokenInterceptor) {
        this.loginMemberArgumentResolver = loginMemberArgumentResolver;
        this.adminRoleCheckInterceptor = adminRoleCheckInterceptor;
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(adminRoleCheckInterceptor)
                .addPathPatterns("/admin/**");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/signup", "/signup/**", "/login", "/login/**", "/logout", "/error");


    }
}
