package br.com.redhat.infrastructure.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.com.redhat.infrastructure.entity.ProductEntity;
import br.com.redhat.infrastructure.entity.RestaurantEntity;
import br.com.redhat.infrastructure.repository.ProductEntityRepository;
import br.com.redhat.infrastructure.repository.RestaurantEntityRepository;
import br.com.redhat.infrastructure.startup.FakeData;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class TestDatabase {
    RestaurantEntityRepository restaurantRepository;
    ProductEntityRepository productRepository;


    public RestaurantEntity restaurantEntity() {
        FakeData fake = FakeData.get();
        RestaurantEntity restaurant = fake.restaurantEntity();
        restaurantRepository.persist(restaurant);

        return restaurant;
    }

    public ProductEntity productEntity(RestaurantEntity restaurant) {
        FakeData fake = FakeData.get();
        ProductEntity product = fake.productEntity(restaurant);
        productRepository.persist(product);

        return product;
    }

    public ProductEntity productEntity() {
        FakeData fake = FakeData.get();
        RestaurantEntity restaurant = restaurantEntity();

        ProductEntity product = fake.productEntity(restaurant);
        productRepository.persist(product);

        return product;
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
