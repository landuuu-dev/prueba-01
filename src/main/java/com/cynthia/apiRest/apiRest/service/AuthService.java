package com.cynthia.apiRest.apiRest.service;
import com.cynthia.apiRest.apiRest.Repositories.UserRepository;
import com.cynthia.apiRest.apiRest.dto.*;
import com.cynthia.apiRest.apiRest.model.Role;
import com.cynthia.apiRest.apiRest.model.User;
import com.cynthia.apiRest.apiRest.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombre(request.getNombre())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtUtils.generateToken((UserDetails) user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        var jwtToken = jwtUtils.generateToken((UserDetails) user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public UserProfileResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return UserProfileResponse.builder()
                .id(currentUser.getId())
                .email(currentUser.getEmail())
                .nombre(currentUser.getNombre())
                .role(currentUser.getRole())
                .build();
    }

    public UserProfileResponse updateUser(UpdateUserRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (request.getNombre() != null) {
            currentUser.setNombre(request.getNombre());
        }

        if (request.getEmail() != null && !request.getEmail().equals(currentUser.getEmail())) {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("El email ya está en uso");
            }
            currentUser.setEmail(request.getEmail());
        }

        if (request.getPassword() != null) {
            currentUser.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(currentUser);

        return UserProfileResponse.builder()
                .id(currentUser.getId())
                .email(currentUser.getEmail())
                .nombre(currentUser.getNombre())
                .role(currentUser.getRole())
                .build();
    }
}