package org.haijun.study.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

// 自定义映射器
public class RecordFieldSetMapper implements FieldSetMapper<Transaction> {
    @Override
    public Transaction mapFieldSet(FieldSet fieldSet) throws BindException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Transaction transaction = new Transaction();

        transaction.setUsername(fieldSet.readString("username"));
        transaction.setUserId(fieldSet.readInt(1));
        transaction.setAmount(fieldSet.readDouble(3));
        String dateString = fieldSet.readString(2);
        try {
            transaction.setTransactionDate(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return transaction;
    }
}
