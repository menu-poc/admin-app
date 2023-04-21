package br.com.redhat.domain.model.product;

import java.util.List;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.redhat.infrastructure.entity.ProductEntity;

@Mapper(
    componentModel = "cdi",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface ProductMapper {
    
    ProductResult toProductResult(ProductEntity entity);
    List<ProductResult> toProductResults(List<ProductEntity> entities);

    @Mapping(target = "restaurant", source = "restaurant.id")
    Product toProduct(ProductEntity entity);
    
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "restaurant.id", source = "restaurant")
    ProductEntity toEntity(Product product);

    @Mapping(target = "restaurant.id", source = "restaurant")
    void update(Product product, @MappingTarget ProductEntity entity);
}
