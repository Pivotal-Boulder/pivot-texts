package com.pivotallabs.pivottexts.pivotsconnector;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PivotsConnectorTest {

    RestTemplate restTemplate;

    private PivotsConnector connector;

    @Before
    public void setup() {
        restTemplate = mock(RestTemplate.class);

        connector = new PivotsConnector(
                restTemplate,
                "https://example.com/users.json",
                "test-email",
                "test-auth-token"
        );
    }

    @Test
    public void testGetPivots() {
        Pivot pivot = new Pivot();
        when(restTemplate.getForEntity("https://example.com/users.json?email=test-email&authentication_token=test-auth-token", Pivot[].class))
                .thenReturn(new ResponseEntity<>(new Pivot[]{pivot}, HttpStatus.OK));

        List<Pivot> pivots = connector.getPivots();

        assertThat(pivots.size(), equalTo(1));
        assertThat(pivots.get(0), sameInstance(pivot));
    }

    @Test
    public void testGetPivots_withFailure() {
        Pivot pivot = new Pivot();
        when(restTemplate.getForEntity("https://example.com/users.json?email=test-email&authentication_token=test-auth-token", Pivot[].class))
                .thenReturn(new ResponseEntity<>((Pivot[]) null, HttpStatus.UNAUTHORIZED));

        List<Pivot> pivots = connector.getPivots();

        assertThat(pivots, nullValue());
    }

}
