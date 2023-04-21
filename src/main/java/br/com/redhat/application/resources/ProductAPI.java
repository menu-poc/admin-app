package br.com.redhat.application.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import br.com.redhat.domain.model.product.Product;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ProductAPI {

    @GET
    @Path("/{id}")
    @Operation(summary = "Get product by ID", description = "Returns a product by ID")
    @APIResponse(responseCode = "200", description = "Success")
    @APIResponse(responseCode = "404", description = "Product not found")
    Response product(@PathParam("id") @Parameter(description = "Product ID") Long id);

    @POST
    @Operation(summary = "Create a new product", description = "Creates a new product")
    @APIResponse(responseCode = "201", description = "Product created")
    @APIResponse(responseCode = "400", description = "Invalid input")
    Response create(
                @RequestBody(description = "Product object") Product product, 
                @Context UriInfo uriInfo) throws Exception;

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a product", description = "Updates a product by ID")
    @APIResponse(responseCode = "200", description = "Product updated")
    @APIResponse(responseCode = "400", description = "Invalid input")
    @APIResponse(responseCode = "404", description = "Product not found")
    Response update(
                @PathParam("id") @Parameter(description = "Product ID") Long id,
                @RequestBody(description = "Product object") Product product);

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a product", description = "Deletes a product by ID")
    @APIResponse(responseCode = "204", description = "Product deleted")
    @APIResponse(responseCode = "404", description = "Product not found")
    Response delete(@PathParam("id") @Parameter(description = "Product ID") Long id);
}
