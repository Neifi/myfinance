package es.neifi.myfinance.users.domain;

import es.neifi.myfinance.shared.domain.baseValueObject.StringValueObject;
import es.neifi.myfinance.users.domain.exceptions.InvalidAvatarException;


import java.net.MalformedURLException;
import java.net.URL;

public class Avatar extends StringValueObject {

    public Avatar(String value) {
        super(value);
        if (!validateUrl(value)) {
            throw new InvalidAvatarException("The url " + value + " is invalid");
        }
    }

    private boolean validateUrl(String value) {
        try {
            new URL(null,value);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
