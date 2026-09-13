package com.hdfclife.desk.web;

import com.hdfclife.desk.model.Claim;
import com.hdfclife.desk.model.Policy;
import com.hdfclife.desk.service.ClaimService;
import com.hdfclife.desk.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/policies")
@Tag(
        name = "Policies",
        description = "HDFC Life policy operations"
)

public class PolicyController {
    private final PolicyService policyService;
    private final ClaimService claimService;

    public PolicyController(
            PolicyService policyService,
            ClaimService claimService) {

        this.policyService = policyService;
        this.claimService = claimService;
    }

    @GetMapping
    @Operation(summary = "Get policies")
    @ApiResponse(
            responseCode = "200",
            description = "Policies returned"
    )
    public ResponseEntity<List<Policy>> getPolicies(

            @Parameter(
                    description = "Filter by exact status"
            )
            @RequestParam(required = false)
            String status,

            @Parameter(
                    description = "Filter by exact policy type"
            )
            @RequestParam(required = false)
            String type) {

        if (status == null && type == null) {

            return ResponseEntity.ok(
                    policyService.getAllPolicies()
            );
        }

        return ResponseEntity.ok(
                policyService.findPolicies(status, type)
        );
    }

    @GetMapping("/{policyNo}")
    @Operation(summary = "Get policy by policy number")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Policy found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Policy not found"
            )
    })
    public ResponseEntity<Policy> getPolicy(
            @PathVariable String policyNo) {

        return ResponseEntity.ok(
                policyService.getPolicy(policyNo)
        );
    }

    @PostMapping
    @Operation(summary = "Create a policy")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Policy created"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Duplicate policy"
            )
    })
    public ResponseEntity<Policy> createPolicy(
            @Valid @RequestBody Policy policy) {

        Policy createdPolicy =
                policyService.createPolicy(policy);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{policyNo}")
                .buildAndExpand(
                        createdPolicy.getPolicyNo()
                )
                .toUri();

        return ResponseEntity
                .created(location)
                .body(createdPolicy);
    }

    @PutMapping("/{policyNo}")
    @Operation(summary = "Replace a policy")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Policy updated"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Policy not found"
            )
    })
    public ResponseEntity<Policy> updatePolicy(
            @PathVariable String policyNo,
            @Valid @RequestBody Policy policy) {

        return ResponseEntity.ok(
                policyService.updatePolicy(
                        policyNo,
                        policy
                )
        );
    }

    @DeleteMapping("/{policyNo}")
    @Operation(summary = "Delete a policy")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Policy deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Policy not found"
            )
    })
    public ResponseEntity<Void> deletePolicy(
            @PathVariable String policyNo) {

        policyService.deletePolicy(policyNo);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/{policyNo}/claims")
    @Operation(summary = "Get claims for a policy")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Claims returned"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Policy not found"
            )
    })
    public ResponseEntity<List<Claim>> getClaims(
            @PathVariable String policyNo) {

        return ResponseEntity.ok(
                claimService.getClaimsForPolicy(policyNo)
        );
    }
}
