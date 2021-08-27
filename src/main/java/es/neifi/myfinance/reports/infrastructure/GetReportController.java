package es.neifi.myfinance.reports.infrastructure;

import es.neifi.myfinance.reports.application.ReportFinder;
import es.neifi.myfinance.reports.domain.Report;
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

    @GetMapping("/user/report/{reportId}")
    public ResponseEntity<?> getReport(@PathVariable("reportId") String reportId, @Nullable @RequestParam String initialDate, @Nullable @RequestParam String endDate) {
        Optional<Report> report = this.reportFinder.findById(reportId);
        if (report.isPresent()) {

            return ok(new ReportResponse(
                    report.get().getTotalExpenses().value(),
                    report.get().getTotalIncomes().value(),
                    report.get().getTotalSavings().value(),
                    report.get().getIsExpense().isTrue(),
                    report.get().getDate().value()));
        }
        return ResponseEntity.notFound().build();
    }

    public final class ReportResponse {

        private Double totalExpenses;
        private Double totalIncomes;
        private Double totalSavings;
        private boolean isExpense;
        private Long date;

        public ReportResponse( Double totalExpenses, Double totalIncomes, Double totalSavings, Boolean isExpense, Long date) {
            this.totalExpenses = totalExpenses;
            this.totalIncomes = totalIncomes;
            this.totalSavings = totalSavings;
            this.isExpense = isExpense;
            this.date = date;
        }


        public Double getTotalExpenses() {
            return totalExpenses;
        }

        public Double getTotalIncomes() {
            return totalIncomes;
        }

        public Double getTotalSavings() {
            return totalSavings;
        }

        public boolean isExpense() {
            return isExpense;
        }

        public Long getDate() {
            return date;
        }
    }
}

