package teste.autoflex.vitorcsouza.prodmanager.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teste.autoflex.vitorcsouza.prodmanager.domain.dto.*;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.Role;
import teste.autoflex.vitorcsouza.prodmanager.domain.model.User;
import teste.autoflex.vitorcsouza.prodmanager.domain.repository.UserRepository;
import teste.autoflex.vitorcsouza.prodmanager.domain.service.AuthService;
import teste.autoflex.vitorcsouza.prodmanager.infra.exceptions.EmailAlreadyExistsException;
import teste.autoflex.vitorcsouza.prodmanager.infra.security.JwtService;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public AuthRes login(AuthReq req) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.password()));
            User user = userRepository.findByEmail(req.email())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + req.email()));

            String token = jwtService.generateToken(user);
            return new AuthRes(token);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @Override
    @Transactional
    public UserDTORes register(UserDTOReq dto) {
        User user = dto.toEntity();
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered: " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        return UserDTORes.fromEntity(userRepository.save(user));
    }

    @Override
    @Transactional
    public UpdateAuthRes updateUser(UUID userId, UpdateAuthReq req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));

        if (req.username() != null) {
            user.setUsername(req.username());
        }

        if (req.email() != null) {
            user.setEmail(req.email());
        }

        if (req.password() != null) {
            user.setPassword(passwordEncoder.encode(req.password()));
        }

        userRepository.save(user);

        return UpdateAuthRes.fromEntity(user);
    }
}
