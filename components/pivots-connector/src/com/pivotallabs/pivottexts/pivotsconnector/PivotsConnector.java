package com.pivotallabs.pivottexts.pivotsconnector;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static java.util.Arrays.asList;

public class PivotsConnector implements PivotsSource {

    private RestTemplate restTemplate;
    String pivotsUrl;
    String pivotsEmail;
    String pivotsAuthToken;

    public PivotsConnector(RestTemplate restTemplate, String pivotsUrl, String pivotsEmail, String pivotsAuthToken) {
        this.restTemplate = restTemplate;
        this.pivotsUrl = pivotsUrl;
        this.pivotsEmail = pivotsEmail;
        this.pivotsAuthToken = pivotsAuthToken;
    }

    public List<Pivot> getPivots() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(pivotsUrl)
            .queryParam("email", pivotsEmail)
            .queryParam("authentication_token", pivotsAuthToken);

        ResponseEntity<Pivot[]> response = restTemplate.getForEntity(uriBuilder.toUriString(), Pivot[].class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        return asList(response.getBody());
    }
}
