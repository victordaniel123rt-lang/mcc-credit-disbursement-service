package com.vdgarcia.mcc_credit_disbursement_service.dto;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositDTO {

    private String accountNumber;
    private BigDecimal amount;
    private String customerCu;

}
