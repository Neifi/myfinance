package es.neifi.myfinance.registry.application.exceptions;

import es.neifi.myfinance.shared.application.exception.ApplicationException;

public class RegistryNotFoundException  extends ApplicationException {
    public RegistryNotFoundException(String id) {
        super("Registry not found with ID: " + id);
    }
}
