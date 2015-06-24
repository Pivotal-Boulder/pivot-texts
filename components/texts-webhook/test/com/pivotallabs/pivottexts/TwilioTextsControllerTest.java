package com.pivotallabs.pivottexts;

import com.pivotallabs.pivottexts.pivottextsservice.PivotTextsService;
import com.pivotallabs.pivottexts.pivottextsservice.TextMessage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class TwilioTextsControllerTest {
    @Mock
    PivotTextsService service;

    @InjectMocks
    TwilioTextsController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void testSaveMessage() throws Exception {
        TextMessage text = new TextMessage("+13213459876", "Had to pick up a latte, I'll be late");

        when(service.saveText(text)).thenReturn(true);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/twilio-texts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("From", "+13213459876")
                        .param("Body", "Had to pick up a latte, I'll be late")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().xml("<Response></Response>"))
        ;
    }

    @Test
    public void testSaveMessage_withMissingParams() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/twilio-texts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("From", "")
                        .param("Body", "")
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
        ;

        verify(service, never()).saveText(any());
    }

    @Test
    public void testSaveMessage_withFailure() throws Exception {
        when(service.saveText(any())).thenReturn(false);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/twilio-texts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("From", "+155555555555")
                        .param("Body", "Had to pick up a latte, I'll be late")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().xml("<Response>" +
                        "<Message>We could not display your message. Is your number on Pivots?</Message>" +
                        "</Response>"))
        ;
    }
}
