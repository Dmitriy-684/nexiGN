package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "subscribers")
public class SubscriberEntity {
    @Id
    @Column(name = "phone", nullable = false, unique = true, length = 11)
    private String phone;

    @OneToMany(mappedBy = "subscriberEntity")
    private Set<TransactionEntity> transactionEntitySet = new HashSet<>();
    public void addTransaction(TransactionEntity transactionEntity){
        transactionEntitySet.add(transactionEntity);
        transactionEntity.setSubscriberEntity(this);
    }
    @Override
    public String toString(){
        StringBuilder entitySet = new StringBuilder();
        for (TransactionEntity transaction : transactionEntitySet) entitySet.append(transaction.toString()).append(", ");
        return "SubscriberEntity{phone=" + phone +
                ", transactionEntitySet={" + entitySet + "}}";
    }
}
