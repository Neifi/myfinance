package es.neifi.myfinance.savings.application;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SavingListResponse {

    private List<SavingResponse> savingResponses;
    private double totalSaved;
    private String[] timePeriod;

}
