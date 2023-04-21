package br.com.redhat.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import br.com.redhat.domain.model.page.PageResult;
import br.com.redhat.domain.model.product.Product;
import br.com.redhat.domain.model.product.ProductResult;
import br.com.redhat.infrastructure.data.TestDatabase;
import br.com.redhat.infrastructure.entity.ProductEntity;
import br.com.redhat.infrastructure.entity.RestaurantEntity;
import br.com.redhat.infrastructure.repository.ProductEntityRepository;
import br.com.redhat.infrastructure.repository.RestaurantEntityRepository;
import br.com.redhat.infrastructure.startup.FakeData;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import lombok.AllArgsConstructor;

@QuarkusTest
@TestTransaction
@AllArgsConstructor
public class ProductServiceTest {
    
    RestaurantEntityRepository restaurantRepository;
    ProductEntityRepository repository;
    TestDatabase database;
    ProductService service;

    @Test
    public void shouldCreateProduct() {
        FakeData fake = FakeData.get();
        RestaurantEntity restaurant = database.restaurantEntity();

        Product product = fake.product(restaurant.getId());
        product = service.create(product);

        assertEquals(1, repository.count());
        assertNotNull(product.getId());
    }

    @Test
    public void shouldUpdateProduct() {
        FakeData fake = FakeData.get();
        ProductEntity productEntity = database.productEntity();
        BigDecimal firstPrice = productEntity.getPrice();

        Product product = service.product(productEntity.getId()).orElse(new Product());
        String price = fake.getFaker().commerce().price().replace(",", ".");
        product.setPrice(new BigDecimal(price));
        product = service.update(product);
        
        assertEquals(1, repository.count());
        assertEquals(product.getName(), productEntity.getName());
        assertNotEquals(firstPrice, product.getPrice());
        assertEquals(price, product.getPrice().toPlainString());
    }

    @Test
    public void shouldFindProductById() {
        ProductEntity productEntity = database.productEntity();

        assertEquals(1, repository.count());
        
        Optional<Product> product = service.product(productEntity.getId());
        assertTrue(product.isPresent());
    }


    @Test
    public void shouldNotFindProductByInvalidId() {
        database.productEntity();
        
        assertEquals(1, repository.count());
        
        long invalidId = 999L;
        Optional<Product> product = service.product(invalidId);
        assertTrue(product.isEmpty());
    }

    @Test
    public void shouldReturnProductsResultsPaginated() {
        database.productEntities(22);

        PageResult<ProductResult> page1 = service.all(1, 5);
        assertEquals(1, page1.getPage());
        assertEquals(5, page1.getNumberOfPages());
        assertEquals(5, page1.getResults().size());

        PageResult<ProductResult> page4 = service.all(5, 5);
        assertEquals(5, page4.getPage());
        assertEquals(5, page4.getNumberOfPages());
        assertEquals(2, page4.getResults().size());

        PageResult<ProductResult> page6 = service.all(6, 5);
        assertEquals(6, page6.getPage());
        assertEquals(5, page6.getNumberOfPages());
        assertEquals(0, page6.getResults().size());
    }
}
