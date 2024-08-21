package com.devsuperior.dscommerce.tests;

import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;

import java.time.LocalDate;
import java.util.HashSet;

public class UserFactory {

    public static User createClientUser() {
        User user = User.builder()
                .id(1L)
                .name("Maria")
                .email("maria@gmail.com")
                .phone("999999999")
                .birthDate(LocalDate.parse("2001-07-25"))
                .password("$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO")
                .roles(new HashSet<>())
                .build();
        user.addRole(new Role(1L, "ROLE_CLIENT"));
        return user;
    }

    public static User createAdminUser() {
        User user = User.builder()
                .id(2L)
                .name("Alex")
                .email("Alex@gmail.com")
                .phone("977777777")
                .birthDate(LocalDate.parse("1987-12-13"))
                .password("$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO")
                .roles(new HashSet<>())
                .build();
        user.addRole(new Role(2L, "ROLE_ADMIN"));
        return user;
    }

    public static User createCustomClientUser(Long id, String username) {
        User user = User.builder()
                .id(id)
                .name("Maria")
                .email(username)
                .phone("999999999")
                .birthDate(LocalDate.parse("2001-07-25"))
                .password("$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO")
                .roles(new HashSet<>())
                .build();
        user.addRole(new Role(1L, "ROLE_CLIENT"));
        return user;
    }

    public static User createCustomAdminUser(Long id, String username) {
        User user = User.builder()
                .id(id)
                .name("Alex")
                .email(username)
                .phone("977777777")
                .birthDate(LocalDate.parse("1987-12-13"))
                .password("$2a$10$N7SkKCa3r17ga.i.dF9iy.BFUBL2n3b6Z1CWSZWi/qy7ABq/E6VpO")
                .roles(new HashSet<>())
                .build();
        user.addRole(new Role(2L, "ROLE_ADMIN"));
        return user;
    }
}
