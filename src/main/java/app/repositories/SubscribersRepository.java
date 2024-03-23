package app.repositories;

import app.entities.SubscriberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface that implements methods of working with subscribers in the database
 */

@Repository
public interface SubscribersRepository extends JpaRepository<SubscriberEntity, String> {

}
