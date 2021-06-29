package es.neifi.myfinance.registry.application.exceptions;

public class RegistryNotFoundException extends RuntimeException {
    public RegistryNotFoundException(String id) {
        super("Registry not found with ID: " + id);
    }
}
