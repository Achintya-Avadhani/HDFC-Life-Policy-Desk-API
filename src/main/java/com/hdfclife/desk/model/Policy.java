package com.hdfclife.desk.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Policy {

    @NotBlank
    private String policyNo;

    @NotBlank
    private String customer;

    @NotBlank
    private String type;

    @Min(1)
    private int basePremium;

    @NotBlank
    private String status;

}
