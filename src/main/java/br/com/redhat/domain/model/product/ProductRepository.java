package br.com.redhat.domain.model.product;

import java.util.List;

import br.com.redhat.infrastructure.entity.ProductEntity;

public interface ProductRepository {
    
    public List<ProductEntity> fromRestaurant(Long id);
}
