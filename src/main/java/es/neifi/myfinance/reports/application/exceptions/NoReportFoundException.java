package es.neifi.myfinance.reports.application.exceptions;

import es.neifi.myfinance.shared.application.exception.ApplicationException;

public class NoReportFoundException extends ApplicationException {

    public NoReportFoundException() {
        super("No reports found");
    }

    public NoReportFoundException(String reportId) {
        super("No report found with ID: "+reportId);
    }
}
