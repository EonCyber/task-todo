package com.br.tasks.adapters.rest;

import com.br.tasks.application.port.rest.AuthRest;
import com.br.tasks.application.service.AuthService;
import com.br.tasks.domain.contract.login.AuthResponseDTO;
import com.br.tasks.domain.contract.login.LoginRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Endpoints for authentication and authorization")
public class AuthController implements AuthRest {
    @Autowired
    AuthService authService;

    @Override
    public ResponseEntity<AuthResponseDTO> login(@RequestBody() LoginRequestDTO request)  {
        AuthResponseDTO response = authService.loginUser(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
