package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.repositories.UserRepository;
import com.devsuperior.dscommerce.services.exceptions.UserNotLoggedException;
import com.devsuperior.dscommerce.tests.UserDetailsFactory;
import com.devsuperior.dscommerce.tests.UserFactory;
import com.devsuperior.dscommerce.utils.CustomUserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag("Unit")
@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserServices userServices;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomUserUtil userUtil;

    private String existingUserName, nonExistingUserName;
    private User user;
    private List<UserDetailsProjection> userDetails;

    @BeforeEach
    void setUp() {
        existingUserName = "existingUserName@gmail.com";
        nonExistingUserName = "nonExistingUserName@gmail.com";

        user = UserFactory.createCustomAdminUser(1L, existingUserName);
        userDetails = UserDetailsFactory.createCustomClientUser(existingUserName);

        Mockito.when(userRepository.searchUserAndRolesByEmail(existingUserName)).thenReturn(userDetails);
        Mockito.when(userRepository.searchUserAndRolesByEmail(nonExistingUserName)).thenReturn(new ArrayList<>());
        Mockito.when(userRepository.findByEmail(existingUserName)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmail(nonExistingUserName)).thenReturn(Optional.empty());
    }

    @Test
    public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
        UserDetails result = userServices.loadUserByUsername(existingUserName);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUsername(), existingUserName);

        Mockito.verify(userRepository, Mockito.times(1)).searchUserAndRolesByEmail(existingUserName);
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userServices.loadUserByUsername(nonExistingUserName);
        });

        Mockito.verify(userRepository, Mockito.times(1)).searchUserAndRolesByEmail(nonExistingUserName);
    }

    @Test
    public void authenticatedShouldReturnUserWhenUserExists() {
        Mockito.when(userUtil.getLoggedUserName()).thenReturn(existingUserName);

        User result = userServices.authenticated();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUsername(), existingUserName);
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(existingUserName);
    }

    @Test
    public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        Mockito.doThrow(ClassCastException.class).when(userUtil).getLoggedUserName();

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userServices.authenticated();
        });
    }

    @Test
    public void authenticatedShouldThrowUserNotLoggedExceptionWhenUserDoesNotExist() {
        Mockito.when(userUtil.getLoggedUserName()).thenReturn(nonExistingUserName);

        Assertions.assertThrows(UserNotLoggedException.class, () -> {
            userServices.authenticated();
        });
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(nonExistingUserName);
    }

    @Test
    public void getCurrentUserShouldReturnUserDTOWhenUserAuthenticated() {
        UserServices spyUserServices = Mockito.spy(userServices);
        Mockito.doReturn(user).when(spyUserServices).authenticated();

        UserDTO result = spyUserServices.getCurrentUser();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getEmail(), existingUserName);
    }

    @Test
    public void getCurrentUserShouldThrowUserNotLoggedExceptionWhenUserNotAuthenticated() {
        UserServices spyUserServices = Mockito.spy(userServices);
        Mockito.doThrow(UserNotLoggedException.class).when(spyUserServices).authenticated();

        Assertions.assertThrows(UserNotLoggedException.class, () -> {
            UserDTO result = spyUserServices.getCurrentUser();
        });
    }
}
