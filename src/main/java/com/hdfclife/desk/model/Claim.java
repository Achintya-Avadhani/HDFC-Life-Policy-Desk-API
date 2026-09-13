package com.hdfclife.desk.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
    private String claimNo;

    @NotBlank
    private String policyNo;

    @Min(1)
    private int claimAmount;

    @NotNull
    private Urgency urgency;

    private String status;
}
