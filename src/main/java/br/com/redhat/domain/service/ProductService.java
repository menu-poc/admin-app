package br.com.redhat.domain.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.redhat.domain.model.page.PageResult;
import br.com.redhat.domain.model.product.Product;
import br.com.redhat.domain.model.product.ProductMapper;
import br.com.redhat.domain.model.product.ProductResult;
import br.com.redhat.infrastructure.entity.ProductEntity;
import br.com.redhat.infrastructure.repository.ProductEntityRepository;

@ApplicationScoped
public class ProductService {
    
    @Inject
    ProductEntityRepository repository;
    
    @Inject
    ProductMapper mapper;
    
    @ConfigProperty(name = "app.default.page-size", defaultValue = "20")
    Integer defaultPageSize;

    public Product create(Product product) {
        ProductEntity entity = repository.create(mapper.toEntity(product));
        return mapper.toProduct(entity);
    }

    public Product update(Product product) {
        ProductEntity entity = repository.findById(product.getId());
        mapper.update(product, entity);
        repository.persist(entity);

        return mapper.toProduct(entity);
    }

    public Optional<Product> product(Long id) {
        return repository.findByIdOptional(id).map(mapper::toProduct);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public PageResult<ProductResult> all(int page) {
        PageResult<ProductEntity> results = repository.all(page, defaultPageSize);
        List<ProductResult> convertedResults = mapper.toProductResults(results.getResults());
        return new PageResult<>(convertedResults, results.getPage(), results.getNumberOfPages());
    }

    public PageResult<ProductResult> all(int page, int pageSize) {
        PageResult<ProductEntity> results = repository.all(page, pageSize);
        List<ProductResult> convertedResults = mapper.toProductResults(results.getResults());
        return new PageResult<>(convertedResults, results.getPage(), results.getNumberOfPages());
    }

    public PageResult<ProductResult> fromRestaurant(Long restaurantId, int page, int pageSize) {
        PageResult<ProductEntity> results = repository.fromRestaurant(restaurantId, page, pageSize);
        List<ProductResult> convertedResults = mapper.toProductResults(results.getResults());
        return new PageResult<>(convertedResults, results.getPage(), results.getNumberOfPages());
    }

    public PageResult<ProductResult> fromRestaurant(Long restaurantId, int page) {
        PageResult<ProductEntity> results = repository.fromRestaurant(restaurantId, page, defaultPageSize);
        List<ProductResult> convertedResults = mapper.toProductResults(results.getResults());
        return new PageResult<>(convertedResults, results.getPage(), results.getNumberOfPages());
    }
}
