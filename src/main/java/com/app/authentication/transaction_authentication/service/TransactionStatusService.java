package com.app.authentication.transaction_authentication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.authentication.transaction_authentication.entity.Transaction;
import com.app.authentication.transaction_authentication.repository.TransactionStatusRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionStatusService {

    Logger logger = LoggerFactory.getLogger(TransactionStatusService.class);

    @Autowired
    TransactionStatusRepository transactionStatusRepository;

    public boolean checkTransactionStatus(Transaction transaction){
        logger.info("checking status for merchentId {}",transaction.getMerchantId());
        double amount_limit = transactionStatusRepository.getAmountByMerchentId(transaction.getMerchantId());
        return (transaction.getAmount()<=amount_limit);
    }
    
}
