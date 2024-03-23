
package app.repositories;

import app.entities.TransactionEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * An interface that implements methods for working with transactions in a database
 */

@Repository
@Qualifier("transaction")
public interface TransactionsRepository extends JpaRepository<TransactionEntity, Long> {

}
