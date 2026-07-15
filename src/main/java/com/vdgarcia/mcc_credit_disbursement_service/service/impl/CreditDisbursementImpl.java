package com.vdgarcia.mcc_credit_disbursement_service.service.impl;

import com.vdgarcia.mcc_credit_disbursement_service.client.IAccountRestClient;
import com.vdgarcia.mcc_credit_disbursement_service.config.PublisherMessageService;
import com.vdgarcia.mcc_credit_disbursement_service.dto.AccountDTO;
import com.vdgarcia.mcc_credit_disbursement_service.dto.CreditDisbursementDTO;
import com.vdgarcia.mcc_credit_disbursement_service.dto.DepositDTO;
import com.vdgarcia.mcc_credit_disbursement_service.entity.CreditDisbursement;
import com.vdgarcia.mcc_credit_disbursement_service.event.CreditDisbursementEvent;
import com.vdgarcia.mcc_credit_disbursement_service.repository.ICreditDisbursementRepository;
import com.vdgarcia.mcc_credit_disbursement_service.service.interfaces.ICreditDisbursementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditDisbursementImpl implements ICreditDisbursementService {

    private final ICreditDisbursementRepository repository;
    private final IAccountRestClient accountRestClient;
    private final PublisherMessageService publisherMessageService;

    @Override
    public List<CreditDisbursementDTO> getAll() {
        return List.of();
    }

    @Override
    public CreditDisbursementDTO add(CreditDisbursementDTO creditDisbursementDTO) {
        log.info("add CreditDisbursement, {}", creditDisbursementDTO);
        ResponseEntity<AccountDTO> responseEntity = this.accountRestClient.depositInAccount(
                DepositDTO.builder()
                        .customerCu(creditDisbursementDTO.getCustomerCu())
                        .accountNumber(creditDisbursementDTO.getAccountNumber())
                        .amount(creditDisbursementDTO.getAmount())
                        .build()

        );
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            log.info("Deposit in account succesful");
            CreditDisbursement creditDisbursement = new CreditDisbursement();
            creditDisbursement.setDTO(creditDisbursementDTO);
            CreditDisbursement saveEntity = this.repository.save(creditDisbursement);
            CreditDisbursementEvent creditDisbursementEvent = CreditDisbursementEvent.builder()
                    .accountNumber(creditDisbursementDTO.getAccountNumber())
                    .amount(creditDisbursementDTO.getAmount())
                    .email("vgarcia@gmail.com")
                    .customerCu(creditDisbursementDTO.getCustomerCu())
                    .build();
            this.publisherMessageService.sendCreditDisbursementEvent(creditDisbursementEvent);
            return repository.save(saveEntity).getDTO();
        }
        return CreditDisbursementDTO.builder().build();
    }

    @Override
    public CreditDisbursementDTO update(CreditDisbursementDTO creditDisbursementDTO) {
        return null;
    }

    @Override
    public CreditDisbursementDTO delete(CreditDisbursementDTO creditDisbursementDTO) {
        return null;
    }

    @Override
    public CreditDisbursementDTO getById(String id) {
        return null;
    }
}
