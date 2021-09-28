package es.neifi.myfinance.shared.domain;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UploadContentShould {

    @Test
    void fail_when_content_is_not_valid() {
        UploadContent uploadContent = new UploadContent("test");
        Exception exception = Assert.assertThrows(IllegalArgumentException.class, uploadContent::getMetadata);

        Assertions.assertThat(exception.getMessage()).isEqualTo("Ensure metadata is not empty, has id, uploadedOn or name as keys.");
    }

}