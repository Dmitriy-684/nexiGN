package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * A class that implements the essence of a subscriber in a database
 * The class has the following fields:
 * phone - subscriber's phone
 * transactionEntitySet - list of all subscriber's transactions
 * The class has the following methods:
 * addTransaction(TransactionEntity transactionEntity) - method that add transaction to transactionEntitySet field
 * toString() - returns information about the subscribers
 */

@Setter
@Getter
@Entity
@Table(name = "subscribers")
public class SubscriberEntity {
    @Id
    @Column(name = "phone", nullable = false, unique = true, length = 11)
    private String phone;

    @OneToMany(mappedBy = "subscriberEntity", fetch = FetchType.EAGER)
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
