package br.com.redhat.application.resources;

import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.redhat.domain.model.restaurant.Restaurant;

@Path("/restaurants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface RestaurantAPI {

    @GET
    @Path("/{id}/products")
    @Operation(summary = "Get products from restaurant", description = "Returns products from restaurant")
    @APIResponse(responseCode = "200", description = "Success")
    @APIResponse(responseCode = "404", description = "Restaurant not found")
    Response fromRestaurant(
        @PathParam("id") @Parameter(description = "Restaurant ID") Long id,
        @QueryParam("page") @DefaultValue("1") @Min(1) Integer page
    );

    @GET
    @Path("/{id}")
    @Operation(summary = "Get restaurant by ID", description = "Returns a restaurant by ID")
    @APIResponse(responseCode = "200", description = "Success")
    @APIResponse(responseCode = "404", description = "Restaurant not found")
    Response restaurant(@PathParam("id") @Parameter(description = "Restaurant ID") Long id);

    @POST
    @Operation(summary = "Create a new restaurant", description = "Creates a new restaurant")
    @APIResponse(responseCode = "201", description = "Restaurant created")
    @APIResponse(responseCode = "400", description = "Invalid input")
    Response create(
                @RequestBody(description = "Restaurant object") Restaurant restaurant);

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a restaurant", description = "Updates a restaurant by ID")
    @APIResponse(responseCode = "200", description = "Restaurant updated")
    @APIResponse(responseCode = "400", description = "Invalid input")
    @APIResponse(responseCode = "404", description = "Restaurant not found")
    Response update(
                @PathParam("id") @Parameter(description = "Restaurant ID") Long id,
                @RequestBody(description = "Restaurant object") Restaurant restaurant);

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a restaurant", description = "Deletes a restaurant by ID")
    @APIResponse(responseCode = "204", description = "Restaurant deleted")
    @APIResponse(responseCode = "404", description = "Restaurant not found")
    Response delete(@PathParam("id") @Parameter(description = "Restaurant ID") Long id);
}
