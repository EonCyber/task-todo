package com.br.tasks.application.port.rest;

import com.br.tasks.domain.contract.login.AuthResponseDTO;
import com.br.tasks.domain.contract.login.LoginRequestDTO;
import com.br.tasks.domain.contract.signup.SignupRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import javax.security.auth.login.AccountNotFoundException;

public interface AuthRest {

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate Credentials",
            description = "Compare credentials with the accounts in the system.",
            requestBody = @RequestBody(
                    required = true,
                    description = "Login Request",
                    content = @Content(schema = @Schema(implementation = SignupRequestDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Login Successfull"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "500", description = "Server error")
            }
    )
    ResponseEntity<AuthResponseDTO> login(LoginRequestDTO request);
}
