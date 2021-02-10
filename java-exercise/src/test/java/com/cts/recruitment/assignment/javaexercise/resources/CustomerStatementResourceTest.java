package com.cts.recruitment.assignment.javaexercise.resources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerStatementResourceTest {

    public static final String TEST_URL = "http://localhost:";
    public static final String TEST_PATH = "/records";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Test health check url of resource class
     */
    @Test
    void healthCheck() {
        String result = this.restTemplate.getForObject(TEST_URL + port + TEST_PATH + "/health/check",
                String.class);
        Assertions.assertEquals("KeepAlive", result);
    }

    /**
     * Test validation /records of resource class
     * This is test to show about calling service using java 11 http client
     * More integration test will be cover in Test module using Karate
     */
    @Test
    void validateStatements() throws IOException, InterruptedException {
        final HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        String json = new StringBuilder()
                .append("[{\"txnReference\":10101,\"accNumber\":\"GB33BUKB20201555555555\"," +
                        "\"startBalance\":\"1000\",\"endBalance\":\"1202\",\"Description\":\"This is test\"}," +
                        "{\"txnReference\":10102,\"accNumber\":\"GB33BUKB20201555555555\",\"startBalance\":\"2000\"," +
                        "\"mutation\":\"100\",\"endBalance\":\"1900\",\"Description\":\"This is test\"}" + "]").toString();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(TEST_URL + port + TEST_PATH))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/json")
                .build();

        httpClient.send(request, HttpResponse.BodyHandlers.discarding());
    }
}