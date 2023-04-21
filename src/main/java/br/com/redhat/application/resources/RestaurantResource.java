package br.com.redhat.application.resources;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.redhat.domain.model.restaurant.Restaurant;
import br.com.redhat.domain.service.ProductService;
import br.com.redhat.domain.service.RestaurantService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class RestaurantResource implements RestaurantAPI {
    
    ProductService products;
    RestaurantService restaurants;
    
    @Counted(value = "menu.resource.restaurants.fromRestaurant.counter")
    @Timed(value = "menu.resource.restaurants.fromRestaurant.timer")
    @Override
    public Response fromRestaurant(Long id, Integer page) {
        return Response.ok(products.fromRestaurant(id, page)).build();
    }

    @Counted(value = "menu.resource.restaurants.restaurant.counter")
    @Timed(value = "menu.resource.restaurants.restaurant.timer")
    @Override
    public Response restaurant(Long id) {
        return Response.ok(restaurants.restaurant(id)).build();
    }

    @Counted(value = "menu.resource.restaurants.create.counter")
    @Timed(value = "menu.resource.restaurants.create.timer")
    @Override
    public Response create(Restaurant restaurant) {
        Restaurant result = restaurants.create(restaurant);
        URI uri = UriBuilder.fromResource(RestaurantAPI.class)
                    .path(String.format("/%s", result.getId()))
                    .build();
        return Response.created(uri).build();
    }

    @Counted(value = "menu.resource.restaurants.update.counter")
    @Timed(value = "menu.resource.restaurants.update.timer")
    @Override
    public Response update(Long id, Restaurant restaurant) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Counted(value = "menu.resource.restaurants.delete.counter")
    @Timed(value = "menu.resource.restaurants.delete.timer")
    @Override
    public Response delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
