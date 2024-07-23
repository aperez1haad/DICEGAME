package com.example.JuegoDeDados.Controllers;

import com.example.JuegoDeDados.Security.AuthenticationRequest;
import com.example.JuegoDeDados.Security.AuthenticationResponse;
import com.example.JuegoDeDados.Security.RegisterRequest;
import com.example.JuegoDeDados.Services.AuthenticationService;
import com.example.JuegoDeDados.Utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.AuthenticationControllerRequestMapping)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(Constants.register)
    @Operation(summary = Constants.registerSwaggerSummary, description = Constants.registerSwaggerDscr)
    public ResponseEntity<AuthenticationResponse> register(
            @Parameter(description = Constants.registerSwaggerParam, required = true)
            @RequestBody RegisterRequest request
    ){
        return  ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(Constants.authenticate)
    @Operation(summary = Constants.authenticateSwaggerSummary, description = Constants.authenticateSwaggerDscr)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Parameter(description = Constants.authenticateSwaggerParam, required = true)
            @RequestBody AuthenticationRequest request
    ){
        return  ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
