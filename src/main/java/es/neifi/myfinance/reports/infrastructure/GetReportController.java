package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.reports.application.ReportFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetReportController {

    @Autowired
    private ReportFinder reportFinder;

    @GetMapping("/user/{userId}/report")
    public ResponseEntity<?> getReport(@PathVariable("userId") String userId, @Nullable @RequestParam String initialDate, @Nullable @RequestParam String endDate){
        return ResponseEntity.ok("{\"totalExpenses\":100,\"totalIncomes\":1100,\"totalSavings\":1000,\"date\":\"22/06/2021}\"}");
    }
}
