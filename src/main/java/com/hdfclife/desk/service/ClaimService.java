package com.hdfclife.desk.service;


import com.hdfclife.desk.config.HdfcProperties;
import com.hdfclife.desk.exception.ClaimNotFoundException;
import com.hdfclife.desk.exception.InvalidClaimException;
import com.hdfclife.desk.model.Claim;
import com.hdfclife.desk.model.Urgency;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClaimService {
    private final PolicyService policyService;
    private final HdfcProperties hdfcProperties;

    private final List<Claim> claims = new ArrayList<>();

    public ClaimService(
            PolicyService policyService,
            HdfcProperties hdfcProperties) {

        this.policyService = policyService;
        this.hdfcProperties = hdfcProperties;
    }

    public Claim createClaim(
            String policyNo,
            int claimAmount,
            Urgency urgency) {

        policyService.getPolicy(policyNo);

        if (claimAmount <= 0 ||
                claimAmount > hdfcProperties.getMaxClaimAmount()) {

            throw new InvalidClaimException(
                    "Invalid claim amount: " + claimAmount
            );
        }

        String claimNo = String.format(
                "CLM-%02d",
                claims.size() + 1
        );

        Claim claim = new Claim(
                claimNo,
                policyNo,
                claimAmount,
                urgency,
                "SUBMITTED"
        );

        claims.add(claim);

        return claim;
    }

    public Claim getClaim(String claimNo) {

        return claims.stream()
                .filter(claim ->
                        claim.getClaimNo().equals(claimNo)
                )
                .findFirst()
                .orElseThrow(() ->
                        new ClaimNotFoundException(
                                "Claim not found: " + claimNo
                        )
                );
    }

    public List<Claim> getClaimsForPolicy(
            String policyNo) {

        // Also verifies policy exists
        policyService.getPolicy(policyNo);

        return claims.stream()
                .filter(claim ->
                        claim.getPolicyNo().equals(policyNo)
                )
                .toList();
    }
}
