package br.com.redhat.domain.service;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import br.com.redhat.application.validation.exception.RestaurantNotFoundException;
import br.com.redhat.domain.model.restaurant.Restaurant;
import br.com.redhat.domain.model.restaurant.RestaurantMapper;
import br.com.redhat.infrastructure.entity.RestaurantEntity;
import br.com.redhat.infrastructure.repository.RestaurantEntityRepository;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class RestaurantService {
    
    RestaurantEntityRepository repository;

    RestaurantMapper mapper;

    Logger log;

    public Restaurant create(Restaurant restaurant) {
        RestaurantEntity entity = repository.create(mapper.toEntity(restaurant));
        return mapper.toRestaurant(entity);
    }

    public void update(Long id, Restaurant restaurant) {
        Optional<RestaurantEntity> findByIdOptional = repository.findByIdOptional(id);
        RestaurantEntity entity = findByIdOptional.orElseThrow(RestaurantNotFoundException::new);
        mapper.merge(restaurant, entity);
        repository.update(entity);
    }

    public Restaurant restaurant(Long id) {
        Optional<RestaurantEntity> optional = repository.findByIdOptional(id);
        return optional.map(mapper::toRestaurant).orElseThrow(RestaurantNotFoundException::new);
    }

    public boolean delete(Long id) {
        repository.findByIdOptional(id).orElseThrow(RestaurantNotFoundException::new);
        return repository.deleteById(id);
    }
}
