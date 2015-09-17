package com.pivotallabs.pivottexts.pivottextsservice;

import com.pivotallabs.pivottexts.textsdatastorage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PivotTextsController {

    @Autowired
    PivotTextsDataGateway dataGateway;

    @RequestMapping(value = "/pivot-texts/today", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<PivotText> today() {
        return dataGateway.forToday();
    }

}
