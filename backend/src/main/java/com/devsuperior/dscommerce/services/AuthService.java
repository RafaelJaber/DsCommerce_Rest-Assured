package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserServices userServices;

    public AuthService(UserServices userServices) {
        this.userServices = userServices;
    }

    public void validateSelfOrAdmin(Long userId) {
        User me = userServices.authenticated();
        if (me.hasRole("ROLE_ADMIN")) {
            return;
        }
        if (!me.getId().equals(userId)) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }
    }
}
