package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Setter
@Getter
@Entity
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubscriberEntity subscriberEntity;

    @Column(name = "call_type", nullable = false, length = 2)
    private String callType;

    @Column(name = "start_call", nullable = false)
    private String startCall;

    @Column(name = "end_call", nullable = false)
    private String endCall;

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
                ", endCall=" + endCall + "}";
    }
}
