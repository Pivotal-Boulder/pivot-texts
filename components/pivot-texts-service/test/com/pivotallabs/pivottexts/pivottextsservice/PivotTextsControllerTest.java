package com.pivotallabs.pivottexts.pivottextsservice;

import com.pivotallabs.pivottexts.textsdatastorage.*;
import org.junit.*;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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

        String expectedJson = "[{" +
            "pivotId: 32," +
            "pivotLocation: \"Boulder\"," +
            "pivotFirstName: \"Johnny\"," +
            "pivotLastName: \"Doe++\"," +
            "message: \"Chicken Pox AGAIN!!!\"," +
            "receivedAt: 123" +
            "}]";
        mockMvc.perform(get("/pivot-texts/today").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedJson));
    }
}
