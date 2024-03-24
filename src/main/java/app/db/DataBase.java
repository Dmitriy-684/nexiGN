package app.db;

import app.entities.SubscriberEntity;
import app.entities.TransactionEntity;
import app.databaseServices.SubscribersService;
import app.databaseServices.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;

/**
 * A class that implements the H2 database service
 * The class contains the fields:
 * subscribersServices - a service for working with the subscribers table
 * transactionsService - a service for working with the transaction table
 * The class contains methods:
 * saveAllSubscribersTelephone(Set<String> phones) - saves all subscribers' phones to the database
 * saveSubscribersTransactions(HashMap<String, ArrayList<String>> transactions, Set<String> phone, int month) - saves all transactions of one subscriber to the database
 */

@Service("baseH2")
@RequiredArgsConstructor
public class DataBase {
    private final SubscribersService subscribersService;

    private final TransactionsService transactionsService;
    public void saveAllSubscribersTelephones(Set<String> phones) {
        for (String phone : phones){
            SubscriberEntity subscriberEntity = new SubscriberEntity();
            subscriberEntity.setPhone(phone);
            subscribersService.save(subscriberEntity);
        }
    }
    public void saveSubscribersTransactions(HashMap<String, ArrayList<String>> transactions, Set<String> phones, int month){
        for (String phone : phones){
            List<TransactionEntity> transactionEntities = new ArrayList<>();
            Optional<SubscriberEntity> subscriberEntity = subscribersService.getByPhone(phone);
            for (String transaction : transactions.get(phone)){
                String[] data = transaction.split(",");
                TransactionEntity transactionEntity = new TransactionEntity();
                transactionEntity.setCallType(data[0]);
                transactionEntity.setStartCall(data[2].strip());
                transactionEntity.setEndCall(data[3].strip());
                transactionEntity.setMonth(String.valueOf(Month.of(month)));
                transactionEntities.add(transactionEntity);
                subscriberEntity.ifPresent(subscriberEntity1 -> subscriberEntity1.addTransaction(transactionEntity));
            }
            subscriberEntity.ifPresent(subscribersService::save);
            for (TransactionEntity transactionEntity : transactionEntities) transactionsService.save(transactionEntity);
            transactionEntities.clear();
        }
    }
}
