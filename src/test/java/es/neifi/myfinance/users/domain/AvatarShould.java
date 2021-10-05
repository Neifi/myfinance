package es.neifi.myfinance.users.domain;

import es.neifi.myfinance.users.domain.exceptions.InvalidAvatarException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AvatarShould {

    @Test
    void throw_exception_when_url_is_mal_formed() {
        Exception exception = Assertions.assertThrows(InvalidAvatarException.class,() ->{
            new Avatar("badurl");
        });
    }

    @Test
    void save_url_if_is_well_formed() {
        String url = "https://www.RFC2396.es/RFC2396";
        Avatar avatar = new Avatar(url);

        assertThat(url).isEqualTo(avatar.value());
    }
}