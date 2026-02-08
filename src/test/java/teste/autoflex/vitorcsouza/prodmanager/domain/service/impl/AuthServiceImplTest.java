package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.*;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Role;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.User;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.UserRepository;
import teste.autoflex.vitorcsouza.prodmanager.infra.exceptions.EmailAlreadyExistsException;
import teste.autoflex.vitorcsouza.prodmanager.infra.security.JwtService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private AuthenticationManager authManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private User defaultUser;

    @BeforeEach
    void setup() {
        defaultUser = User.builder()
                .id(UUID.randomUUID())
                .username("vii")
                .email("vii@test.com")
                .password("123")
                .role(Role.USER)
                .build();
    }

    @Test
    void shouldLoginSuccessfullyAndReturnToken() {
        AuthReq req = new AuthReq("vii@test.com", "123");

        when(userRepository.findByEmail(req.email()))
                .thenReturn(Optional.of(defaultUser));

        when(jwtService.generateToken(defaultUser))
                .thenReturn("fake-jwt");

        AuthRes res = authService.login(req);

        assertNotNull(res.token());
        assertEquals("fake-jwt", res.token());
    }

    @Test
    void shouldThrowExceptionWhenEmailNotFound() {
        AuthReq req = new AuthReq("x@test.com", "123");

        when(userRepository.findByEmail(req.email()))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> authService.login(req));
    }

    @Test
    void shouldThrowExceptionWhenCredentialsAreInvalid() {
        AuthReq req = new AuthReq("vii@test.com", "wrong");

        doThrow(BadCredentialsException.class)
                .when(authManager)
                .authenticate(any());

        assertThrows(BadCredentialsException.class,
                () -> authService.login(req));
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        UserDTOReq req = new UserDTOReq(
                "vii",
                "Password123",
                "vii@test.com",
                Role.USER
        );

        when(userRepository.findByEmail(req.email()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("Password123"))
                .thenReturn("hashed");

        when(userRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        UserDTORes res = authService.register(req);

        assertEquals("vii", res.username());
        assertEquals("vii@test.com", res.email());
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        UserDTOReq req = new UserDTOReq(
                "vii",
                "Password123",
                "vii@test.com",
                Role.USER
        );

        when(userRepository.findByEmail(req.email()))
                .thenReturn(Optional.of(defaultUser));

        assertThrows(EmailAlreadyExistsException.class,
                () -> authService.register(req));
    }

    @Test
    void shouldUpdateUsernameOnly() {
        UpdateAuthReq req = new UpdateAuthReq("newname", null, null);

        when(userRepository.findById(defaultUser.getId()))
                .thenReturn(Optional.of(defaultUser));

        UpdateAuthRes res =
                authService.updateUser(defaultUser.getId(), req);

        assertEquals("newname", res.username());
        assertEquals("vii@test.com", res.email());
    }

    @Test
    void shouldUpdateEmailAndPassword() {
        UpdateAuthReq req =
                new UpdateAuthReq(null, "new@test.com", "NewPass123");

        when(userRepository.findById(defaultUser.getId()))
                .thenReturn(Optional.of(defaultUser));

        when(passwordEncoder.encode("NewPass123"))
                .thenReturn("hashed");

        UpdateAuthRes res =
                authService.updateUser(defaultUser.getId(), req);

        assertEquals("new@test.com", res.email());
        assertEquals("hashed", res.password());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UpdateAuthReq req = new UpdateAuthReq("x", null, null);

        when(userRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> authService.updateUser(UUID.randomUUID(), req));
    }
}