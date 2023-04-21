package br.com.redhat.infrastructure.repository;

import javax.enterprise.context.ApplicationScoped;

import br.com.redhat.infrastructure.entity.RestaurantEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class RestaurantEntityRepository 
    implements PanacheRepositoryBase<RestaurantEntity, Long> {
    
    public RestaurantEntity create(RestaurantEntity restaurant) {
        persist(restaurant);
        return restaurant;
    }

    public RestaurantEntity update(RestaurantEntity restaurant) {
        persist(restaurant);
        return restaurant;
    }
}
