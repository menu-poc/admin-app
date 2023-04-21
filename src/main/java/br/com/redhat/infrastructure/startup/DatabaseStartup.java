package br.com.redhat.infrastructure.startup;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import br.com.redhat.infrastructure.entity.ProductEntity;
import br.com.redhat.infrastructure.entity.RestaurantEntity;
import br.com.redhat.infrastructure.repository.ProductEntityRepository;
import br.com.redhat.infrastructure.repository.RestaurantEntityRepository;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class DatabaseStartup {
    
    Logger log;
    RestaurantEntityRepository restaurantRepository;
    ProductEntityRepository productRepository;


    @Transactional
    void onStart(@Observes StartupEvent ev) {
        log.info("Starting application");
        if(LaunchMode.current().equals(LaunchMode.DEVELOPMENT)) {
            productEntities(FakeData.get().getFaker().number().numberBetween(20, 100));
            productEntities(FakeData.get().getFaker().number().numberBetween(20, 100));
            productEntities(FakeData.get().getFaker().number().numberBetween(20, 100));
            productEntities(FakeData.get().getFaker().number().numberBetween(20, 100));
            productEntities(FakeData.get().getFaker().number().numberBetween(20, 100));
            productEntities(FakeData.get().getFaker().number().numberBetween(20, 100));
            productEntities(FakeData.get().getFaker().number().numberBetween(20, 100));
            productEntities(FakeData.get().getFaker().number().numberBetween(20, 100));
        }
    }

    void onStop(@Observes ShutdownEvent ev) {
        log.info("Stopping application");
    }


    public RestaurantEntity restaurantEntity() {
        FakeData fake = FakeData.get();
        RestaurantEntity restaurant = fake.restaurantEntity();
        restaurantRepository.persist(restaurant);

        return restaurant;
    }

    public List<ProductEntity> productEntities(int quantity) {
        return productEntities(restaurantEntity(), quantity);
    }

    public List<ProductEntity> productEntities(RestaurantEntity restaurant, int quantity) {
        FakeData fake = FakeData.get();
        List<ProductEntity> products = fake.productsEntities(restaurant, quantity);
        productRepository.persist(products);

        return products;
    }
}
