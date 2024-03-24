package app.databaseServices;

import app.entities.TransactionEntity;
import app.repositories.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A class that implements a service for working with a transaction table in a database
 * The class contains a field:
 * transactionsRepository - transaction repository interface
 * The class contains the following methods:
 * save(TransactionEntity transactionEntity) - saving the transaction to the database
 * saveAll(List<TransactionEntity> transactionEntities) - saving all transactions to the database
 * getAll() - getting all transactions
 */

@Service
@RequiredArgsConstructor
public class TransactionsService {
    private final TransactionsRepository transactionsRepository;
    public void save(TransactionEntity transactionEntity){
        transactionsRepository.save(transactionEntity);
    }
    public void saveAll(List<TransactionEntity> transactionEntities) {transactionsRepository.saveAll(transactionEntities);}
    public List<TransactionEntity> getAll(){
        return transactionsRepository.findAll();
    }
}
