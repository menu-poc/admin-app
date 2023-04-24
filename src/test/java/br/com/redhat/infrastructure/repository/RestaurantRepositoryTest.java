package br.com.redhat.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import br.com.redhat.infrastructure.entity.RestaurantEntity;
import br.com.redhat.infrastructure.startup.FakeData;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@QuarkusTest
@TestTransaction
@QuarkusTestResource(H2DatabaseTestResource.class)
public class RestaurantRepositoryTest {
    
    private RestaurantEntityRepository repository;
    private ProductEntityRepository productRepository;

    @Test
    public void shouldCreateRestaurant() {
        RestaurantEntity restaurant = FakeData.get().restaurantEntity();
        repository.persist(restaurant);
        
        assertNotNull(restaurant.getId());
    }

    @Test
    public void shouldCreateRestaurantWithProducts() {
        int numProducts = 5;
        RestaurantEntity restaurant = FakeData.get().restaurantEntity();
        restaurant.setProducts(FakeData.get().productsEntities(restaurant, numProducts));
        repository.persist(restaurant);

        Optional<RestaurantEntity> result = repository.findByIdOptional(restaurant.getId());

        assertEquals(numProducts, result.map(r -> r.getProducts().size()).orElse(-1));
    }

    @Test
    public void shouldListRestaurants() {
        int quantity = 20;
        repository.persist(FakeData.get().restaurantsEntities(quantity));

        long count = repository.count();
        assertEquals(count, quantity);
    }

    @Test
    public void shouldFindARestaurantWithId() {
        RestaurantEntity restaurant = FakeData.get().restaurantEntity();
        repository.persist(FakeData.get().restaurantsEntities(10));
        repository.persist(restaurant);

        assertTrue(repository.findByIdOptional(restaurant.getId()).isPresent());
    }

    @Test
    public void shouldReturnEmptyOnFindARestaurantWithNonExistingId() {
        repository.persist(FakeData.get().restaurantsEntities(10));

        long notExistingId = 999L;
        assertTrue(repository.findByIdOptional(notExistingId).isEmpty());
    }

    @Test
    public void shouldReturnDeleteARestaurant() {
        RestaurantEntity restaurant = FakeData.get().restaurantEntity();
        repository.persist(FakeData.get().restaurantsEntities(10));
        repository.persist(restaurant);

        assertEquals(repository.count(), 11);

        repository.deleteById(restaurant.getId());

        assertEquals(repository.count(), 10);
    }

    @Test
    public void shouldReturnDeleteARestaurantWithProducts() {
        int numProducts = 5;
        RestaurantEntity restaurant = FakeData.get().restaurantEntity();
        restaurant.setProducts(FakeData.get().productsEntities(restaurant, numProducts));
        repository.persist(restaurant);

        assertEquals(5, productRepository.count());

        repository.deleteById(restaurant.getId());

        assertEquals(0, productRepository.count());
    }
}
