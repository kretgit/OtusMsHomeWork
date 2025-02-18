package ru.otus.ms.userservice.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.ms.userservice.util.Headers;

import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class SecurityController {

    @GetMapping("auth")
    public AuthenticationResponse authenticate(Authentication authentication, HttpServletResponse response) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new BadCredentialsException("Для входа требуеся аутентификация");
        }

        UserDetails details = (UserDetails) authentication.getPrincipal();
        response.addHeader(Headers.ADMIN_HEADER.getHeaderName(), Headers.fillAdminHeader(details));
        response.addHeader(Headers.EMAIL_HEADER.getHeaderName(), details.getUsername());
        return new AuthenticationResponse("Welcome to Junk-Food Application!");
    }
}
