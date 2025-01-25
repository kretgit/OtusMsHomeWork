package ru.otus.ms.common.utils.rest;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.ms.common.exception.CommonException;
import ru.otus.ms.common.utils.Integration;


@UtilityClass
@Slf4j
public class RestUtils {

    public static <T> T sendHttpRequest(RestTemplate restTemplate,
                                        HttpMethod httpMethod,
                                        String url,
                                        @Nullable HttpHeaders additionalHeaders,
                                        ParameterizedTypeReference<T> returnType,
                                        @Nullable Object requestBody) {

        HttpEntity<?> httpEntity = new HttpEntity<>(requestBody, addHttpHeaders(additionalHeaders));
        ResponseEntity<T> responseEntity;

        log.info("try {} request to {}", httpMethod, url);
        log.debug("with requestBody {}", ObjectUtils.isEmpty(requestBody) ? "" : Integration.toGson(requestBody));
        try {
            responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, returnType);
        } catch (RestClientException e) {
            log.error("error request to {},\n{}", url, e);
            throw new CommonException(e);
        }

        log.info("success request to {}", url);
        return responseEntity.getBody();
    }

    private HttpHeaders addHttpHeaders(HttpHeaders addHttpHeaders) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        if (!ObjectUtils.isEmpty(addHttpHeaders)) {
            httpHeaders.addAll(addHttpHeaders);
        }
        return httpHeaders;
    }

    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public String composeUrl(String baseUrl, String ...params) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl).pathSegment(params).toUriString();
    }

}
