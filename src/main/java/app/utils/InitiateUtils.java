package app.utils;

import app.entities.SubscriberEntity;
import app.entities.TransactionEntity;
import app.services.SubscribersService;
import app.services.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitiateUtils implements CommandLineRunner {
    private final SubscribersService subscribersService;
    private final TransactionsService transactionsService;
    @Override
    public void run(String... args) throws Exception {
        SubscriberEntity subscriberEntity = new SubscriberEntity();
        subscriberEntity.setPhone("89303569144");
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setCallType("01");
        transactionEntity.setStartCall("174");
        transactionEntity.setEndCall("175");
        subscriberEntity.addTransaction(transactionEntity);
        subscribersService.save(subscriberEntity);
        transactionsService.save(transactionEntity);
        System.out.println(subscriberEntity);
    }
}
