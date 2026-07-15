package com.vdgarcia.mcc_credit_disbursement_service.entity;

import com.vdgarcia.mcc_credit_disbursement_service.dto.CreditDisbursementDTO;
import com.vdgarcia.mcc_credit_disbursement_service.util.IMapper;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "t_credit_disbursement")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreditDisbursement implements IMapper<CreditDisbursementDTO> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, length = 60)
    private String id;
    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;
    @Column(name = "term_months", nullable = false, length = 60)
    private Integer termMonths;
    @Column(name = "account_number", nullable = false, length = 30)
    private String accountNumber;
    @Column(name = "interest_rate", precision = 10, scale = 2)
    private BigDecimal interestRate;
    @Column(name = "customer_cu", nullable = false, length = 20)
    private String customerCu;

    @Override
    public CreditDisbursementDTO getDTO() {
        return CreditDisbursementDTO.builder()
                .id(id)
                .accountNumber(accountNumber)
                .amount(amount)
                .interestRate(interestRate)
                .termMonths(termMonths)
                .customerCu(customerCu)
                .build();
    }

    @Override
    public void setDTO(CreditDisbursementDTO creditDisbursementDTO) {
        this.id = creditDisbursementDTO.getId();
        this.customerCu = creditDisbursementDTO.getCustomerCu();
        this.accountNumber = creditDisbursementDTO.getAccountNumber();
        this.termMonths = creditDisbursementDTO.getTermMonths();
        this.amount = creditDisbursementDTO.getAmount();
        this.interestRate = creditDisbursementDTO.getInterestRate();
    }
}
