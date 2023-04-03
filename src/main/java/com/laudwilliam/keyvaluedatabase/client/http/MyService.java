package com.laudwilliam.keyvaluedatabase.client.http;

import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class MyService {


    public void makeGetRequest() {
        WebClient webClient = WebClient.create();

        // Make the GET request and get the response body
        String responseBody = webClient.get()
                .uri("https://google.com")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Print the response body
        System.out.println(responseBody);
    }
}

