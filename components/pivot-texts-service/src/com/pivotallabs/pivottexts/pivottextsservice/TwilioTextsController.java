package com.pivotallabs.pivottexts.pivottextsservice;

import com.twilio.sdk.verbs.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class TwilioTextsController {

    private final Logger logger = getLogger(this.getClass());

    @Autowired
    PivotTextsService pivotTextsService;

    @RequestMapping(value = "/twilio-texts", method = RequestMethod.POST, produces = MediaType.TEXT_XML_VALUE)
    public ResponseEntity<String> receiveText(@ModelAttribute("From") String from,
                                              @ModelAttribute("Body") String body) throws IOException, TwiMLException {
        logger.debug("Create a PivotText from: {}  body: {}", from, body);

        TwiMLResponse twiMLResponse = new TwiMLResponse();

        if (StringUtils.isEmpty(from) || StringUtils.isEmpty(body)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!pivotTextsService.saveText(new TextMessage(from, body))) {
            twiMLResponse.append(new Message("We could not display your message. Is your number on Pivots?"));
        }

        return new ResponseEntity<>(twiMLResponse.toXML(), HttpStatus.OK);
    }
}
