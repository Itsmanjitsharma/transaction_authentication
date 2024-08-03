package com.app.authentication.transaction_authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.authentication.transaction_authentication.entity.Transaction;


@Repository
public interface TransactionStatusRepository extends JpaRepository<Transaction,Integer>{
    
   
    public Transaction findByTransactionId(int transactionId);

    @Query(value = "SELECT max_transaction_amount_limit FROM merchant_details WHERE merchant_id = :merchantId", nativeQuery = true)
    double getAmountByMerchentId(@Param("merchantId") int merchantId);
 
}
