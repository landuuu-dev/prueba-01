package com.cynthia.apiRest.apiRest.Controllers;
import com.cynthia.apiRest.apiRest.Repositories.UserRepository;
import com.cynthia.apiRest.apiRest.dto.*;
import com.cynthia.apiRest.apiRest.model.Role;
import com.cynthia.apiRest.apiRest.model.User;
import com.cynthia.apiRest.apiRest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser() {
        return ResponseEntity.ok(authService.getCurrentUser());
    }

    @PutMapping("/update")
    public ResponseEntity<UserProfileResponse> updateUser(
            @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(authService.updateUser(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/promote-to-admin")
    public ResponseEntity<String> promoteToAdmin(@RequestParam String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return ResponseEntity.ok("Usuario promovido a ADMIN");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll(); // Obtiene todos los usuarios
        return ResponseEntity.ok(users); // Retorna la lista de usuarios
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        User user = userRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        userRepository.delete(user);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }


}