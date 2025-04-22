package com.cynthia.apiRest.apiRest.Controllers;
import com.cynthia.apiRest.apiRest.dto.*;
import com.cynthia.apiRest.apiRest.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

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
}