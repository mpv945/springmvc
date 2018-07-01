package org.haijun.study.batch;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Transaction, Transaction> {

    @Override
    public Transaction process(Transaction transaction) throws Exception {
        return transaction;
    }
}
