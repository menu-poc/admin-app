package br.com.redhat.application.validation.exception;

public class RestaurantNotFoundException extends BusinessException {
    
    public RestaurantNotFoundException() {
        super(1, "Restaurant not found");
    }
}
