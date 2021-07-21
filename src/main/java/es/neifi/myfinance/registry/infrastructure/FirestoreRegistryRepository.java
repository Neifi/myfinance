package es.neifi.myfinance.registry.infrastructure;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import es.neifi.myfinance.registry.domain.Registry;
import es.neifi.myfinance.registry.domain.RegistryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FirestoreRegistryRepository implements RegistryRepository {

    Firestore firestore = FirestoreClient.getFirestore();

    public FirestoreRegistryRepository() {

    }

    @Override
    public Optional<Registry> searchExpenseById(String id) {
        ApiFuture<QuerySnapshot> query = firestore.collection("users").get();


        return Optional.empty();
    }

    @Override
    public void save(Registry registry) {

    }

    @Override
    public List<Registry> search() {
        return null;
    }

    @Override
    public List<Registry> searchIncomes() {
        return null;
    }

    @Override
    public List<Registry> searchExpenses() {
        return null;
    }

    @Override
    public List<Registry> searchExpenseInRange(String initialRange, String endRange) {
        return null;
    }

    @Override
    public List<Registry> searchIncomeInRange(String initialRange, String endRange) {
        return null;
    }

    @Override
    public Optional<Registry> searchIncomeById(String id) {
        return Optional.empty();
    }

    /*
    private List<Registry> sortByDate(DateParser dateParser, List<Registry> registryStream) {
        return registryStream.stream()
                .sorted(Comparator.comparing(expense -> dateParser.parse(expense.getDate().getValue())))
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        ee -> {
                            Collections.reverse(ee);
                            return ee;
                        }));
    }

    private Stream<Registry> filterByDate(String initialDate, String endDate, DateParser dateParser) {
        return registries.values().stream().filter(expense ->
                dateParser.parse(expense.getDate().value())
                        .before(dateParser.parse(endDate))
                        && dateParser.parse(expense.getDate().value())
                        .after(dateParser.parse(initialDate))

        );
    }*/
}
