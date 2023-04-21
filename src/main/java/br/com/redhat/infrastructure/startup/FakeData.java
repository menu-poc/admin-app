package br.com.redhat.infrastructure.startup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.github.javafaker.Faker;

import br.com.redhat.domain.model.product.Product;
import br.com.redhat.domain.model.restaurant.Restaurant;
import br.com.redhat.infrastructure.entity.ProductEntity;
import br.com.redhat.infrastructure.entity.RestaurantEntity;

public class FakeData {
    
    private Faker faker;

    private FakeData() {
        this.faker = new Faker();
    }

    private FakeData(Faker faker) {
        this.faker = faker;
    }

    public static FakeData get() {
        return new FakeData();
    }

    public static FakeData get(Faker faker) {
        return new FakeData(faker);
    }

    public RestaurantEntity restaurantEntity() {
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setName(this.faker.company().name());
        return restaurant;
    }

    public List<RestaurantEntity> restaurantsEntities(int quantity) {
        List<RestaurantEntity> restaurants = new ArrayList<>();
        for (int index = 1; index <= quantity; index++) {
            restaurants.add(this.restaurantEntity());
        }
        return restaurants;
    }

    public ProductEntity productEntity() {
        ProductEntity product = new ProductEntity();
        product.setName(this.faker.food().dish());
        String price = this.faker.commerce().price().replace(",", ".");
        product.setPrice(new BigDecimal(price));
        return product;
    }
    

    public ProductEntity productEntity(RestaurantEntity restaurant) {
        ProductEntity product = productEntity();
        product.setRestaurant(restaurant);
        return product;
    }

    public List<ProductEntity> productsEntities(int quantity) {
        return this.productsEntities(null, quantity);
    }

    public List<ProductEntity> productsEntities(RestaurantEntity restaurant, int quantity) {
        List<ProductEntity> products = new ArrayList<>();
        for (int index = 1; index <= quantity; index++) {
            products.add(this.productEntity(restaurant));
        }
        return products;
    }

    public Restaurant restaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(faker.company().name());
        return restaurant;
    }

    public Product product() {
        return product(null);
    }

    public Product product(Long restaurantId) {
        Product product = new Product();
        product.setName(faker.food().dish());
        String price = this.faker.commerce().price().replace(",", ".");
        product.setPrice(new BigDecimal(price));
        product.setRestaurant(restaurantId);
        return product;
    }

    public Faker getFaker() {
        return this.faker;
    }
}
