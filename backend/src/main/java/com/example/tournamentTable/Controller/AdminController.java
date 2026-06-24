package com.example.tournamentTable.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Operation(
            summary = "Check an admin status",
            description = "Returns a void"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access granted"),
            @ApiResponse(responseCode = "401", description = "Access denied")
    })
    @GetMapping("/private/check")
    public ResponseEntity<Void> checkAdmin(){
        return ResponseEntity.ok().build();
    }
}
