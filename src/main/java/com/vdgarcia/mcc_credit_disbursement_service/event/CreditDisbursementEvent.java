package com.vdgarcia.mcc_credit_disbursement_service.event;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditDisbursementEvent {

    private String accountNumber;
    private String customerCu;
    private BigDecimal amount;
    private String email;
}
