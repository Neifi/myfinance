package es.neifi.myfinance.shared.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode
@Getter
public class Identifier {
    private UUID value;

    public void Identifier(){
        this.value = UUID.randomUUID();
    }

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
