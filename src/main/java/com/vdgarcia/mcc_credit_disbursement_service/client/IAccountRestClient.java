package com.vdgarcia.mcc_credit_disbursement_service.client;

import com.vdgarcia.mcc_credit_disbursement_service.dto.AccountDTO;
import com.vdgarcia.mcc_credit_disbursement_service.dto.DepositDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${feign.clients.account.name}", url = "${feign.clients.account.url}")
public interface IAccountRestClient {

    @PutMapping
    ResponseEntity<AccountDTO> depositInAccount(@RequestBody DepositDTO depositDTO);

}
