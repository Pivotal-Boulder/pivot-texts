package com.pivotallabs.pivottexts.pivottextsservice;

import com.pivotallabs.pivottexts.textsdatastorage.PivotText;
import com.pivotallabs.pivottexts.textsdatastorage.PivotTextsDataGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class PivotTextsControllerTest {
    @Mock
    PivotTextsDataGateway dataGateway;

    @InjectMocks
    PivotTextsController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void testSaveMessage() throws Exception {
        PivotText pivotText = new PivotText();
        pivotText.setPivotId(32);
        pivotText.setPivotLocation("Boulder");
        pivotText.setPivotFirstName("Johnny");
        pivotText.setPivotLastName("Doe++");
        pivotText.setMessage("Chicken Pox AGAIN!!!");
        pivotText.setReceivedAt(123l);

        when(dataGateway.forToday()).thenReturn(asList(pivotText));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/pivot-texts/today")
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{" +
                        "pivotId: 32," +
                        "pivotLocation: \"Boulder\"," +
                        "pivotFirstName: \"Johnny\"," +
                        "pivotLastName: \"Doe++\"," +
                        "message: \"Chicken Pox AGAIN!!!\"," +
                        "receivedAt: 123" +
                        "}]"))
        ;
    }
}
