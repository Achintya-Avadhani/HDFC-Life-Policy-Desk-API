package com.hdfclife.desk.web;

import com.hdfclife.desk.model.Claim;
import com.hdfclife.desk.model.Urgency;
import com.hdfclife.desk.service.ClaimService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/claims")
@Tag(
        name = "Claims",
        description = "HDFC Life claim operations"
)

public class ClaimController {
    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping
    @Operation(summary = "File a claim")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Claim created"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid claim"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Policy not found"
            )
    })
    public ResponseEntity<Claim> createClaim(
            @Valid @RequestBody ClaimRequest request) {

        Claim claim = claimService.createClaim(
                request.getPolicyNo(),
                request.getClaimAmount(),
                request.getUrgency()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{claimNo}")
                .buildAndExpand(claim.getClaimNo())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(claim);
    }

    @GetMapping("/{claimNo}")
    @Operation(summary = "Get claim by claim number")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Claim found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Claim not found"
            )
    })
    public ResponseEntity<Claim> getClaim(
            @PathVariable String claimNo) {

        return ResponseEntity.ok(
                claimService.getClaim(claimNo)
        );
    }

    @Data
    public static class ClaimRequest {

        @NotBlank
        private String policyNo;

        @Min(1)
        private int claimAmount;

        @NotNull
        private Urgency urgency;
    }
}
