package br.com.redhat.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.redhat.infrastructure.entity.ProductEntity;
import br.com.redhat.infrastructure.entity.RestaurantEntity;
import br.com.redhat.infrastructure.startup.FakeData;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@QuarkusTest
@TestTransaction
public class ProductRepositoryTest {
    
    private ProductEntityRepository repository;
    private RestaurantEntityRepository restaurantRepository;

    @Test
    public void shouldNotCreateProductWithoutRestaurant() {
        ProductEntity product = FakeData.get().productEntity();
        assertThrows(javax.persistence.PersistenceException.class, () -> {
            repository.persist(product);
        });
    }

    @Test
    public void shouldNotCreateProductWithDetachedRestaurant() {
        RestaurantEntity restaurant = FakeData.get().restaurantEntity();
        ProductEntity product = FakeData.get().productEntity(restaurant);
        assertThrows(java.lang.IllegalStateException.class, () -> {
            repository.persist(product);
        });
    }

    @Test
    public void shouldCreateProductWithRestaurant() {
        RestaurantEntity restaurant = FakeData.get().restaurantEntity();
        restaurantRepository.persist(restaurant);

        assertNotNull(restaurant.getId());

        ProductEntity product = FakeData.get().productEntity(restaurant);
        repository.persist(product);
        
        assertNotNull(product.getId());
    }

    @Test
    public void shouldListProducts() {
        int numProducts = 20;
        RestaurantEntity restaurant = FakeData.get().restaurantEntity();
        restaurantRepository.persist(restaurant);

        repository.persist(FakeData.get().productsEntities(restaurant, numProducts));

        assertEquals(numProducts, repository.count());
    }

    @Test
    public void shouldListProductsFromRestaurant() {
        int numProductsRestaurantA = 5;
        int numProductsRestaurantB = 8;
        RestaurantEntity restaurantA = FakeData.get().restaurantEntity();
        RestaurantEntity restaurantB = FakeData.get().restaurantEntity();
        restaurantRepository.persist(restaurantA, restaurantB);

        assertNotNull(restaurantA.getId());
        assertNotNull(restaurantB.getId());

        repository.persist(FakeData.get().productsEntities(restaurantA, numProductsRestaurantA));
        repository.persist(FakeData.get().productsEntities(restaurantB, numProductsRestaurantB));

        assertEquals(numProductsRestaurantA, repository.fromRestaurant(restaurantA.getId()).size());
        
        assertEquals(numProductsRestaurantB, repository.fromRestaurant(restaurantB.getId()).size());

        assertEquals(numProductsRestaurantA+numProductsRestaurantB, repository.count());
    }

    @Test
    public void shouldDeleteProduct() {
        int numProductsRestaurant = 5;
        RestaurantEntity restaurant = FakeData.get().restaurantEntity();
        restaurantRepository.persist(restaurant);

        assertNotNull(restaurant.getId());

        ProductEntity product = FakeData.get().productEntity(restaurant);
        List<ProductEntity> products = FakeData.get().productsEntities(restaurant, numProductsRestaurant);
        products.add(product);
        repository.persist(products);

        assertEquals(numProductsRestaurant+1, repository.count());

        repository.deleteById(product.getId());

        assertTrue(repository.findByIdOptional(product.getId()).isEmpty());
    }

    @Test
    public void shouldDeleteProductShouldNotDeleteRestaurant() {
        int numProductsRestaurant = 5;
        RestaurantEntity restaurant = FakeData.get().restaurantEntity();
        restaurantRepository.persist(restaurant);

        assertNotNull(restaurant.getId());

        ProductEntity product = FakeData.get().productEntity(restaurant);
        List<ProductEntity> products = FakeData.get().productsEntities(restaurant, numProductsRestaurant);
        products.add(product);
        repository.persist(products);

        assertEquals(numProductsRestaurant+1, repository.count());

        repository.deleteById(product.getId());

        assertTrue(repository.findByIdOptional(product.getId()).isEmpty());

        assertTrue(restaurantRepository.findByIdOptional(restaurant.getId()).isPresent());
    }
}
