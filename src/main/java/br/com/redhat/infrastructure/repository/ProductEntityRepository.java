package br.com.redhat.infrastructure.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import br.com.redhat.domain.model.page.PageResult;
import br.com.redhat.domain.model.product.ProductRepository;
import br.com.redhat.infrastructure.entity.ProductEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor 
public class ProductEntityRepository 
    implements PanacheRepositoryBase<ProductEntity, Long>,
               ProductRepository {
    
    Logger log;

    @Override
    public List<ProductEntity> fromRestaurant(Long id) {
        return find("restaurant.id", id).list();
    }

    public ProductEntity create(ProductEntity product) {
        persist(product);
        return product;
    }

    public PageResult<ProductEntity> all(int page, int pageSize) {
        PanacheQuery<ProductEntity> query = findAll();
        return paginated(query, page, pageSize);
    }

    public PageResult<ProductEntity> fromRestaurant(Long restaurantId, int page, int pageSize) {
        log.infov("Repository.fromRestaurant({0}, {1}, {2})", restaurantId, page-1, pageSize);
        PanacheQuery<ProductEntity> query = find("restaurant.id", restaurantId);
        return paginated(query, page, pageSize);
    }

    private PageResult<ProductEntity> paginated(PanacheQuery<ProductEntity> query, int page, int pageSize) {
        List<ProductEntity> products = query.page(Page.of(page-1, pageSize)).list();
        int pageCount = query.pageCount();
        log.infov("Query results size: {0}; Pages: {1}", products.size(), pageCount);
        return new PageResult<>(products, page, pageCount);
    }
}