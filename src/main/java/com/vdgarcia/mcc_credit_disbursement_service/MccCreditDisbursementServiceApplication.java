package com.vdgarcia.mcc_credit_disbursement_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MccCreditDisbursementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MccCreditDisbursementServiceApplication.class, args);
	}

}
