package es.neifi.myfinance.reports.exceptions;

public class NoReportFoundException extends RuntimeException{
    public NoReportFoundException() {
        throw new RuntimeException("No reports founded");
    }
}
