package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
