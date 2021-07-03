package es.neifi.myfinance.reports.application.exceptions;

public class NoReportFoundException extends RuntimeException{
    public NoReportFoundException() {
        throw new RuntimeException("No reports founded");
    }
}
