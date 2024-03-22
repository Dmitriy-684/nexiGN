package app.services;

import app.entities.SubscriberEntity;
import app.repositories.SubscribersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribersService {
    private final SubscribersRepository subscribersRepository;
    public void save(SubscriberEntity subscriberEntity){
        subscribersRepository.save(subscriberEntity);
    }
    public void saveAll(List<SubscriberEntity> subscriberEntities) {subscribersRepository.saveAll(subscriberEntities);}
    public List<SubscriberEntity> getAll(){
        return subscribersRepository.findAll();
    }
}
