package com.br.tasks.application.port.rest;

import com.br.tasks.domain.contract.signup.SignupRequestDTO;
import com.br.tasks.domain.contract.login.AuthResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public interface AccountRest {
    @PostMapping
    @Operation(
            summary = "Create a new account",
            description = "Registers a new account in the system",
            requestBody = @RequestBody(
                    required = true,
                    description = "Signup request",
                    content = @Content(schema = @Schema(implementation = SignupRequestDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Account created"),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "500", description = "Server error")
            }
    )
    ResponseEntity<AuthResponseDTO> signUp(SignupRequestDTO request);
}
