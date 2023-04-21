package br.com.redhat.domain.model.restaurant;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.redhat.infrastructure.entity.RestaurantEntity;

@Mapper(
    componentModel = "cdi",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface RestaurantMapper {
    
    Restaurant toRestaurant(RestaurantEntity entity);

    RestaurantEntity toEntity(Restaurant restaurant);

    void merge(Restaurant restaurant, @MappingTarget RestaurantEntity entity);
}
