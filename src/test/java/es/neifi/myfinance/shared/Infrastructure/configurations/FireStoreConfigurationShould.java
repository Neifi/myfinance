package es.neifi.myfinance.shared.Infrastructure.configurations;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FireStoreConfigurationShould {

    @Autowired
    private ApplicationConfig fireStoreConfiguration;

    @Test
    void get_firestore_database() {
        Firestore db = FirestoreClient.getFirestore();
        Assertions.assertNotNull(db);
    }

}