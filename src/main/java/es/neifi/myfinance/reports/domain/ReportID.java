package es.neifi.myfinance.reports.domain;

import es.neifi.myfinance.shared.domain.Identifier;
import lombok.EqualsAndHashCode;

public class ReportID extends Identifier {
    public ReportID(String value) {
        super(value);
    }
}
