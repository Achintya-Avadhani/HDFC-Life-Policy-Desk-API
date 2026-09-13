package com.hdfclife.desk.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "hdfc")
public class HdfcProperties {
    @NotBlank
    private String companyName;

    @Min(1)
    private int maxClaimAmount;
}
