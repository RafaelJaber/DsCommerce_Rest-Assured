package com.devsuperior.dscommerce.tests;

import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsFactory {

    public static List<UserDetailsProjection> createCustomClientUser(String username) {
        List<UserDetailsProjection> users = new ArrayList<>();
        users.add(new UserDetailsImpl(username, "123", 1L, "ROLE_CLIENT"));
        return users;
    }

    public static List<UserDetailsProjection> createCustomAdminClientUser(String username) {
        List<UserDetailsProjection> users = new ArrayList<>();
        users.add(new UserDetailsImpl(username, "123", 1L, "ROLE_CLIENT"));
        users.add(new UserDetailsImpl(username, "123", 2L, "ROLE_ADMIN"));
        return users;
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class UserDetailsImpl implements UserDetailsProjection {
    private String username;
    private String password;
    private Long roleId;
    private String authority;
}
