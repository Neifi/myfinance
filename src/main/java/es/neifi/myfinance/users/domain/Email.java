package es.neifi.myfinance.users.domain;

import es.neifi.myfinance.shared.domain.StringValueObject;
import es.neifi.myfinance.users.domain.exceptions.InvalidEmailException;

public class Email extends StringValueObject {
    public Email(String value) {
        super(value);
        EmailValidator emailValidator = new EmailValidator();
        if (!emailValidator.isValid(value)) {
            throw new InvalidEmailException(value);
        }
    }
}
