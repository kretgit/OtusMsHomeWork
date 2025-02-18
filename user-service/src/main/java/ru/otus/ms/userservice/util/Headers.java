package ru.otus.ms.userservice.util;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public enum Headers {
    ADMIN_HEADER("X_User_Is_Admin"),
    EMAIL_HEADER("X_User_Email"),
    SERVICE_HEADER("X_Service_Name");

    private String headerName;

    Headers(String headerName) {
        this.headerName = headerName;
    }

    public static String fillAdminHeader(UserDetails userDetails) {
        String adminRole = Roles.ROLE_ADMIN.name();
        boolean isAdmin = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(adminRole::equals);
        return isAdmin ? adminRole : null;
    }
}
