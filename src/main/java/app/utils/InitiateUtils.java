package app.utils;

import app.entities.SubscriberEntity;
import app.entities.TransactionEntity;
import app.generate.GenerateCDRFiles;
import app.services.SubscribersService;
import app.services.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitiateUtils implements CommandLineRunner {
    private final SubscribersService subscribersService;
    private final TransactionsService transactionsService;
    @Autowired
    private final GenerateCDRFiles generator;
    @Override
    public void run(String... args) throws Exception {
        for (String phone : generator.generateTelephoneNumbers()) {
            SubscriberEntity subscriberEntity = new SubscriberEntity();
            TransactionEntity transactionEntity = new TransactionEntity();
            subscriberEntity.setPhone(phone);
            subscribersService.save(subscriberEntity);
        }
        for (SubscriberEntity subscriberEntity : subscribersService.getAll()) System.out.println(subscriberEntity.toString());
    }
}
