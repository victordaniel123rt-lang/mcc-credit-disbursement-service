package com.vdgarcia.mcc_credit_disbursement_service.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

    private String id;
    private String cu;
    private String name;
    private String email;
    private String address;
    private String mobile;
}
