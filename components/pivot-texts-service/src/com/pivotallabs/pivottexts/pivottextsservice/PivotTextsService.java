package com.pivotallabs.pivottexts.pivottextsservice;

import com.pivotallabs.pivottexts.pivotsconnector.Pivot;
import com.pivotallabs.pivottexts.pivotsconnector.PivotsSource;
import com.pivotallabs.pivottexts.textsdatastorage.PivotText;
import com.pivotallabs.pivottexts.textsdatastorage.PivotTextsDataGateway;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

public class PivotTextsService {
    PivotTextsDataGateway dataGateway;
    PivotsSource pivotsSource;

    public PivotTextsService(PivotTextsDataGateway dataGateway, PivotsSource pivotsSource) {
        this.dataGateway = dataGateway;
        this.pivotsSource = pivotsSource;
    }

    public boolean saveText(TextMessage text) {
        String cleanFromNumber = last10digits(text.getFromNumber());

        List<Pivot> pivots = pivotsSource.getPivots();

        System.out.println(pivots);
        System.out.println("hey");

        if (pivots == null || pivots.isEmpty()) {
            return false;
        }

        Optional<Pivot> optionalPivot = pivots.stream().filter(p ->
            last10digits(p.getPhone()).equals(cleanFromNumber)
        ).findFirst();

        if (!optionalPivot.isPresent()) {
            return false;
        }

        Pivot pivot = optionalPivot.get();

        PivotText pivotText = new PivotText();
        pivotText.setPivotId(pivot.getId());
        pivotText.setPivotFirstName(pivot.getFirstName());
        pivotText.setPivotLastName(pivot.getLastName());
        pivotText.setPivotLocation(pivot.getLocationName());
        pivotText.setMessage(text.getBody());

        dataGateway.save(pivotText);

        return true;
    }

    private String last10digits(String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber)) {
            return "";
        }

        String numbersOnly = phoneNumber.replaceAll("[^\\d]", "");
        return numbersOnly.substring(Math.max(0, numbersOnly.length() - 10));
    }
}
