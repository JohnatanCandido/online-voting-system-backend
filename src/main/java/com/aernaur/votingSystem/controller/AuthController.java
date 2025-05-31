package com.aernaur.votingSystem.controller;

import com.aernaur.votingSystem.dto.AuthenticationDTO;
import com.aernaur.votingSystem.dto.LoginResponseDTO;
import com.aernaur.votingSystem.entity.Login;
import com.aernaur.votingSystem.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Login) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
