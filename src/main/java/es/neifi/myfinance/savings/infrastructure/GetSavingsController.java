package es.neifi.myfinance.savings.infrastructure;

import es.neifi.myfinance.savings.application.SavingListResponse;
import es.neifi.myfinance.savings.application.SavingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetSavingsController {

    @GetMapping("/users/{userID}/registry/savings")
    public ResponseEntity getSavings(@PathVariable String userID,
                                     @Nullable @RequestParam String initialDate,
                                     @Nullable @RequestParam String endDate) {

        SavingResponse saving1 = SavingResponse.builder()
                .id("787f28f2-003a-4445-8659-d60683107845")
                .saved(1300.00)
                .currency("EUR")
                .date("31/06/2021")
                .build();

        SavingResponse saving2 = SavingResponse.builder()
                .id("787f28f2-003a-4445-8659-d60683107845")
                .saved(800.00)
                .currency("EUR")
                .date("31/07/2021")
                .build();
        SavingListResponse savings = SavingListResponse.builder()
                .savingResponses(List.of(saving1,saving2))
                .totalSaved(2700.00)
                .timePeriod(new String[]{"31/06/2021","31/07/2021"})
                .build();

        return ResponseEntity.ok(savings);
    }

}
