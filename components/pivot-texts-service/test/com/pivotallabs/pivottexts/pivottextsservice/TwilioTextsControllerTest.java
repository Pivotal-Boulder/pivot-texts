package com.pivotallabs.pivottexts.pivottextsservice;

import org.junit.*;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TwilioTextsControllerTest {
    @Mock
    PivotTextsService service;

    @InjectMocks
    TwilioTextsController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testSaveMessage() throws Exception {
        TextMessage text = new TextMessage("+13213459876", "Had to pick up a latte, I'll be late");

        when(service.saveText(text)).thenReturn(true);

        mockMvc.perform(post("/twilio-texts")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("From", "+13213459876")
                .param("Body", "Had to pick up a latte, I'll be late"))
            .andExpect(status().isOk())
            .andExpect(content().xml("<Response></Response>"))
        ;
    }

    @Test
    public void testSaveMessage_withMissingParams() throws Exception {
        mockMvc.perform(post("/twilio-texts")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("From", "")
            .param("Body", ""))
            .andExpect(status().isBadRequest());

        verify(service, never()).saveText(any());
    }

    @Test
    public void testSaveMessage_withFailure() throws Exception {
        when(service.saveText(any())).thenReturn(false);

        mockMvc.perform(
            post("/twilio-texts")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("From", "+155555555555")
                .param("Body", "Had to pick up a latte, I'll be late"))
            .andExpect(status().isOk())
            .andExpect(content().xml("<Response>" +
                "<Message>We could not display your message. Is your number on Pivots?</Message>" +
                "</Response>"));
    }
}
