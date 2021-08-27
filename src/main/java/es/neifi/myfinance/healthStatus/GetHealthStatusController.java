package es.neifi.myfinance.healthStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GetHealthStatusController {

    @GetMapping("/health")
    public Map getHealthStatus(){
        Map<String,String> statusMap = new HashMap<>();
        statusMap.put("status","ok");

        return statusMap;
    }

}
