package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * A class that implements the essence of a transaction in a database
 * The class has the following fields:
 * id - transaction id
 * subscriberEntity - reference to the table of subscribers
 * callType - type of call in transaction
 * startCall - time of the start of the call
 * endCall - the end time of the call
 * month - the month in which the call was made
 * The class has the following methods:
 * setSubscriberIdentity(SubscriberIdentity subscriberIdentity) - sets a link to the table of subscribers
 * String toString() - returns information about the transaction
 */

@Setter
@Getter
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne()
    private SubscriberEntity subscriberEntity;

    @Column(name = "call_type", nullable = false, length = 2)
    private String callType;

    @Column(name = "start_call", nullable = false)
    private String startCall;

    @Column(name = "end_call", nullable = false)
    private String endCall;

    @Column(name = "month_", nullable = false)
    private String month;

    public void setSubscriberEntity(SubscriberEntity subscriberEntity){
        this.subscriberEntity = subscriberEntity;
    }
    @Override
    public String toString(){
        return "TransactionEntity{" +
                "id=" + id +
                ", callType=" + callType +
                ", phone=" +  subscriberEntity.getPhone() +
                ", startCall=" + startCall +
                ", endCall=" + endCall +
                ", month= " + month + "}";
    }
}
