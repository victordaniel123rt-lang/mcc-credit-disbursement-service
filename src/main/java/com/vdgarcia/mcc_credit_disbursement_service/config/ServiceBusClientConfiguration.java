package com.vdgarcia.mcc_credit_disbursement_service.config;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ServiceBusClientConfiguration {

    @Value("${azure.servicebus.connection-string}")
    private String CONNECTION_STRING;
    @Value("${azure.servicebus.queue-name}")
    private String QUEUE_NAME;


    @Bean
    ServiceBusClientBuilder serviceBusClientBuilder(){
        return new ServiceBusClientBuilder()
                .connectionString(CONNECTION_STRING);
    }

    @Bean
    ServiceBusSenderClient serviceBusSenderClient(ServiceBusClientBuilder serviceBusClientBuilder){
        return serviceBusClientBuilder
                .sender()
                .queueName(QUEUE_NAME)
                .buildClient();
    }
}
