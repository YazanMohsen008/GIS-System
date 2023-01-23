package com.fiteprojects.fitegis.Utils.Services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class HttpService {

    public Object doGet(String url, Map<String, String> headers, Map<String, String> Params, Class responseType) {
        RestTemplate restTemplate = new RestTemplate();

        //set the Headers for Request
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity entity = null;
        if (headers != null) {
            headers.forEach(httpHeaders::set);
            entity = new HttpEntity(httpHeaders);
        }
        // Setting http params

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (Params != null && Params.size() > 0)
            Params.forEach(builder::queryParam);
        ResponseEntity response = null;
        String Uri = builder.build(false).toUriString();
        try {
            response =
                    restTemplate.exchange(Uri,
                            HttpMethod.GET,
                            entity,
                            responseType);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return response;
        }
    }
}
