package es.neifi.myfinance.reports.application.exceptions;

public class NoReportFoundException extends RuntimeException{

    public NoReportFoundException() {
        super("No reports found");
    }

    public NoReportFoundException(String reportId) {
        super("No report found with ID: "+reportId);
    }
}
