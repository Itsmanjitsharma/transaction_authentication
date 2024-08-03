package com.app.authentication.transaction_authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.authentication.transaction_authentication.entity.Transaction;
import com.app.authentication.transaction_authentication.service.TransactionStatusService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TransactionStatusController {
    
    @Autowired
    TransactionStatusService transactionStatusService;

    @PostMapping("/status")
    public ResponseEntity<Boolean> getTransactionStatus(@RequestBody Transaction transaction){
        Boolean status = transactionStatusService.checkTransactionStatus(transaction);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
