package com.pivotallabs.pivottexts.textsdatastorage;

import java.util.List;

public interface PivotTextsDataGateway {
    List<PivotText> forToday();
    void save(PivotText pivotText);
}
