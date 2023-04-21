package br.com.redhat.application.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.redhat.domain.model.product.Product;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@ApplicationScoped
public class ProductResource implements ProductAPI {
    
    @Counted(value = "menu.resource.products.product.counter")
    @Timed(value = "menu.resource.products.product.timer")
    @Override
    public Response product(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'product'");
    }

    @Counted(value = "menu.resource.products.create.counter")
    @Timed(value = "menu.resource.products.create.timer")
    @Override
    public Response create(Product product, UriInfo uriInfo) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Counted(value = "menu.resource.products.update.counter")
    @Timed(value = "menu.resource.products.update.timer")
    @Override
    public Response update(Long id, Product product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Counted(value = "menu.resource.products.delete.counter")
    @Timed(value = "menu.resource.products.delete.timer")
    @Override
    public Response delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
