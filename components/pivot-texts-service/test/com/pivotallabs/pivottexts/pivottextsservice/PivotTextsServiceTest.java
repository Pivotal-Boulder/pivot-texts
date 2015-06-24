package com.pivotallabs.pivottexts.pivottextsservice;

import com.pivotallabs.pivottexts.pivotsconnector.Pivot;
import com.pivotallabs.pivottexts.pivotsconnector.PivotsSource;
import com.pivotallabs.pivottexts.textsdatastorage.PivotText;
import com.pivotallabs.pivottexts.textsdatastorage.PivotTextsDataGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PivotTextsServiceTest {
    @Mock
    PivotTextsDataGateway dataGateway;

    @Mock
    PivotsSource pivotsSource;

    @InjectMocks
    PivotTextsService pivotTextsService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSavingAPivotText() {
        Pivot pivot = new Pivot();
        pivot.setId(32);
        pivot.setPhone("+1.415.335.8786");
        pivot.setLocationName("Boulder");
        pivot.setFirstName("Johnny");
        pivot.setLastName("Doe++");

        when(pivotsSource.getPivots()).thenReturn(asList(pivot));

        TextMessage text = new TextMessage("+14153358786", "Chicken Pox AGAIN!!!");
        assertTrue(pivotTextsService.saveText(text));

        PivotText expectedPivotText = new PivotText();
        expectedPivotText.setPivotId(32);
        expectedPivotText.setPivotLocation("Boulder");
        expectedPivotText.setPivotFirstName("Johnny");
        expectedPivotText.setPivotLastName("Doe++");
        expectedPivotText.setMessage("Chicken Pox AGAIN!!!");

        verify(dataGateway).save(expectedPivotText);
    }

    @Test
    public void testSavingAPivotText_whenPivotIsNotFound() {
        when(pivotsSource.getPivots()).thenReturn(new ArrayList<>());

        TextMessage text = new TextMessage("+14153358786", "Chicken Pox AGAIN!!!");
        assertFalse(pivotTextsService.saveText(text));

        verify(dataGateway, never()).save(any());
    }
}
