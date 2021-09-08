package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.reports.application.ReportFinder;
import es.neifi.myfinance.reports.application.ReportResponse;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.users.application.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class GetReportController {

    private ReportFinder reportFinder;

    public GetReportController(ReportFinder reportFinder) {
        this.reportFinder = reportFinder;
    }

    @GetMapping("/user/{userId}/report/{reportId}")
    public ResponseEntity<?> getReport(@PathVariable("userId") String userId, @PathVariable("reportId") String reportId, @Nullable @RequestParam String initialDate, @Nullable @RequestParam String endDate) {
        try {
            Optional<Report> report = this.reportFinder.findById(userId, reportId);
            if (report.isPresent()) {

                return ok(new ReportResponse(
                        report.get().getTotalExpenses().value(),
                        report.get().getTotalIncomes().value(),
                        report.get().getTotalSavings().value(),
                        report.get().getIsExpense().isTrue(),
                        report.get().getDate().value()));
            }
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserNotFoundException(userId));
        }
        return ResponseEntity.notFound().build();
    }



}

