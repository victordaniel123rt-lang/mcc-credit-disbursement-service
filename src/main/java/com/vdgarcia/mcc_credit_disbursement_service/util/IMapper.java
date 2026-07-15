package com.vdgarcia.mcc_credit_disbursement_service.util;

public interface IMapper<T> {
    T getDTO();
    void setDTO(T t);
}
