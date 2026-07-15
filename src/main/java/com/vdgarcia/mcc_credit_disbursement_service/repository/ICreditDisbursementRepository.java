package com.vdgarcia.mcc_credit_disbursement_service.repository;

import com.vdgarcia.mcc_credit_disbursement_service.entity.CreditDisbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditDisbursementRepository extends JpaRepository<CreditDisbursement, String>  {
}
