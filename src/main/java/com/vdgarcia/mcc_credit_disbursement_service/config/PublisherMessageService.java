package com.vdgarcia.mcc_credit_disbursement_service.config;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdgarcia.mcc_credit_disbursement_service.entity.CreditDisbursement;
import com.vdgarcia.mcc_credit_disbursement_service.event.CreditDisbursementEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublisherMessageService {
    private final ServiceBusSenderClient senderClient;
    private final ObjectMapper objectMapper;


    public void sendCreditDisbursementEvent(CreditDisbursementEvent creditDisbursementEvent){
    try{
        String payload = objectMapper.writeValueAsString(creditDisbursementEvent);
        ServiceBusMessage serviceBusMessage = new ServiceBusMessage(payload);
        senderClient.sendMessage(serviceBusMessage);
        log.info("Message sent");
    }catch (Exception ex){
        log.error("Error while sending CreditDisbursement Event", ex);
    }
    }
}
