package app.databaseServices;

import app.entities.SubscriberEntity;
import app.repositories.SubscribersRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * A class that implements a service for working with a subscribers table in a database
 * The class contains a field:
 * subscribersRepository - subscriber repository interface
 * The class contains the following methods:
 * save(SubscriberEntity subscriberEntity) - saving the subscriber to the database
 * saveAll(List<SubscriberEntity> subscriberEntities) - saving all subscribers to the database
 * getAll() - getting all subscribers
 * getByPhone(String phone) - getting subscriber by phone
 */

@Service
@RequiredArgsConstructor
public class SubscribersService {
    private final SubscribersRepository subscribersRepository;
    public void save(SubscriberEntity subscriberEntity){subscribersRepository.save(subscriberEntity);}
    public void saveAll(List<SubscriberEntity> subscriberEntities) {subscribersRepository.saveAll(subscriberEntities);}
    public List<SubscriberEntity> getAll(){
        return subscribersRepository.findAll();
    }
    public Optional<SubscriberEntity> getByPhone(String phone) {return  subscribersRepository.findById(phone);}
}
