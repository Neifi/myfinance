package es.neifi.myfinance.registry.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FirestoreRegistryRepositoryShould {

    @Autowired
    FirestoreRegistryRepository firestoreRegistryRepository;

    @Test
    void searchExpenseById() {
    }
}