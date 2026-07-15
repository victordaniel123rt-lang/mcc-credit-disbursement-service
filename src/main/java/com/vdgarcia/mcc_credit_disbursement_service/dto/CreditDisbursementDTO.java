package com.vdgarcia.mcc_credit_disbursement_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditDisbursementDTO {
    private String id;
    private BigDecimal amount;
    private Integer termMonths;
    private BigDecimal interestRate;
    private String accountNumber;
    private String customerCu;
}
