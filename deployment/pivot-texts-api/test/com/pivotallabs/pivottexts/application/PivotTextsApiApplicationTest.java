package com.pivotallabs.pivottexts.application;

import com.pivotallabs.pivottexts.dbtestinglibrary.TestDataSourceFactory;
import com.pivotallabs.pivottexts.textsdatastorage.PivotText;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockserver.junit.MockServerRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.*;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.*;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PivotTextsApiApplication.class)
@WebIntegrationTest({"server.port=0"})
@ActiveProfiles("test")
public class PivotTextsApiApplicationTest {
    @Value("${local.server.port}")
    int port;

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(8089, this);

    @Before
    public void setup() {
        new TestDataSourceFactory("pivot-texts-test");
    }

    /*
      Testing /twilio-texts:
        Need a better way to test this; one in which users don't have to give valid pivots.pivotallabs.com auth creds in
        pivot-texts-api/test/resources/application.yml. Consider simulator?
    */
    /*
      Testing /pivot-texts/today:
        This would only work if you test put something in the database first.
    */
    @Test
    public void acceptanceTest() {
//        RestTemplate restTemplate = new TestRestTemplate("basic-username", "basic-password");
//
//        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
//        form.add("From", "+15983435434");
//        form.add("Body", "Flat tire :(");
//
//        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
//        headers.add("Accept", MediaType.TEXT_XML_VALUE);
//
//        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(form, headers);
//
//        String postUrl = "http://localhost:" + port + "/twilio-texts";
//        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(postUrl, requestEntity, String.class);
//        assertThat(stringResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));
//
//        String getUrl = "http://localhost:" + port + "/pivot-texts/today";
//        PivotText[] pivotTexts = restTemplate.getForObject(getUrl, PivotText[].class);
//
//        assertThat(pivotTexts.length, equalTo(1));
//
//        PivotText pivotText = pivotTexts[0];
//        assertThat(pivotText.getPivotId(), Matchers.equalTo(123));
//        assertThat(pivotText.getPivotFirstName(), Matchers.equalTo("John"));
//        assertThat(pivotText.getPivotLastName(), Matchers.equalTo("Doe"));
//        assertThat(pivotText.getPivotLocation(), Matchers.equalTo("Boulder"));
//        assertThat(pivotText.getMessage(), Matchers.equalTo("Flat tire :("));
//        assertThat(pivotText.getReceivedAt(), notNullValue());
    }
}
