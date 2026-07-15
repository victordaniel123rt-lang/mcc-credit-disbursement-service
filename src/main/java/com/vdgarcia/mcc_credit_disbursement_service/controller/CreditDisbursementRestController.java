package com.vdgarcia.mcc_credit_disbursement_service.controller;


import com.vdgarcia.mcc_credit_disbursement_service.dto.CreditDisbursementDTO;
import com.vdgarcia.mcc_credit_disbursement_service.service.interfaces.ICreditDisbursementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credit-disbursement")
@RequiredArgsConstructor
public class CreditDisbursementRestController {

    private final ICreditDisbursementService service;

    @GetMapping
    public List<CreditDisbursementDTO> getAllCreditDisbursement(){
    return service.getAll();
    }


    @PostMapping
    public CreditDisbursementDTO addCreditDisbursement(@RequestBody CreditDisbursementDTO dto){
        return this.service.add(dto);
    }
}
