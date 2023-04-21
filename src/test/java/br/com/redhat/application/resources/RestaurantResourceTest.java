package br.com.redhat.application.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import br.com.redhat.domain.model.restaurant.Restaurant;
import br.com.redhat.domain.service.RestaurantService;
import br.com.redhat.infrastructure.startup.FakeData;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import lombok.AllArgsConstructor;

@QuarkusTest
@AllArgsConstructor
public class RestaurantResourceTest {

    @InjectMock
    RestaurantService service;
    RestaurantResource resource;

    @Test
    public void shouldReturnResponseCreatedOnPOST() {
        Restaurant restaurantInput = FakeData.get().restaurant();
        Restaurant restaurantReturn = restaurantInput;
        restaurantReturn.setId(1L);

        when(service.create(any())).thenReturn(restaurantReturn);

        Response response = resource.create(restaurantReturn);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void shouldFindRestaurantOnGetById() {
        Long id = 1L;
        Restaurant restaurantInput = FakeData.get().restaurant();
        restaurantInput.setId(id);

        when(service.restaurant(anyLong())).thenReturn(restaurantInput);

        Response response = resource.restaurant(id);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());
    }

}
