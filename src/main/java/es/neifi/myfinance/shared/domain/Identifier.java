package es.neifi.myfinance.shared.domain;

import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode
public class Identifier {
    private UUID value;

    public Identifier(String value) {
        isValidUUIDOrThrow(value);
        this.value = UUID.fromString(value);
    }

    private void isValidUUIDOrThrow(String value) {
        UUID.fromString(value);
    }

    public String value(){
        return this.value.toString();
    }
}
