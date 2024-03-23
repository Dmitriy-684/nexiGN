package app.databaseServices;

import app.entities.TransactionEntity;
import app.repositories.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
