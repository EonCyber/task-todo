package com.br.tasks.adapters.rest;


import com.br.tasks.application.port.rest.AccountRest;
import com.br.tasks.application.service.AccountService;
import com.br.tasks.domain.contract.login.AuthResponseDTO;
import com.br.tasks.domain.contract.signup.SignupRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@Tag(name = "Accounts", description = "Endpoints for account operations")
public class AccountController implements AccountRest {
    @Autowired
    AccountService accountService;

    @Override
    public ResponseEntity<AuthResponseDTO> signUp(@RequestBody() SignupRequestDTO request) {
        AuthResponseDTO response = accountService.createAccountAndAuthenticate(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);


    }
}
