package teste.autoflex.vitorcsouza.prodmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.*;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Role;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.User;
import teste.autoflex.vitorcsouza.prodmanager.domain.service.AuthService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints responsible for user authentication, authorization and account management")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with a specific role.")
    public ResponseEntity<UserDTORes> register(@RequestBody @Valid UserDTOReq req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates credentials and returns a JWT token.")
    public ResponseEntity<AuthRes> login(@RequestBody @Valid AuthReq req) {
        AuthRes login = authService.login(req);
        return ResponseEntity.ok(login);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update user data", description = "Allows a user to update their own data or an ADMIN to update any user.")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UpdateAuthRes> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateAuthReq req
    ) {
        User loggedUser = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (!loggedUser.getId().equals(id) && loggedUser.getRole() != Role.ADMIN) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(authService.updateUser(id, req));
    }
}
