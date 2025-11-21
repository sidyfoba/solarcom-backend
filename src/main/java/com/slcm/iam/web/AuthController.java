package com.slcm.iam.web;

import com.slcm.iam.dto.LoginRequest;
import com.slcm.iam.dto.LoginResponse;
import com.slcm.iam.security.JwtTokenService;
import com.slcm.iam.security.SecurityUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    	System.out.println("test in functon login");
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityUser principal = (SecurityUser) auth.getPrincipal();
        List<String> permissions = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", principal.getUserId());
        claims.put("internalUser", principal.isInternalUser());
        claims.put("permissions", permissions);

        String token = jwtTokenService.generateToken(claims, principal);

        LoginResponse resp = new LoginResponse()
                .setToken(token)
                .setUsername(principal.getUsername())
                .setPermissions(permissions);

        return ResponseEntity.ok(resp);
    }
}
