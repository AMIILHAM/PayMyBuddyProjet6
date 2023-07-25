package com.demos.paymybuddy.mapper;

import com.demos.paymybuddy.domain.Transaction;
import com.demos.paymybuddy.dto.TransactionDto;

public interface TransactionMapper {

    TransactionDto transactionToTransactionDto(Transaction transaction);
}
