package es.neifi.myfinance.reports.application;

import es.neifi.myfinance.registry.domain.vo.*;
import es.neifi.myfinance.reports.domain.Report;
import es.neifi.myfinance.reports.domain.ReportID;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class ReportSaverShoud {

    private ReportSaver reportSaver;


    void save_report() throws ParseException {

        Report report = Report.builder()
                .reportID(new ReportID("c51cd0f2-e260-4286-8fcd-9e410a329f35"))
                .totalExpenses(100)
                .totalSavings(1000)
                .date(new Date("27/11/2021"))
                .build();

        this.reportSaver.save(report);

    }

}