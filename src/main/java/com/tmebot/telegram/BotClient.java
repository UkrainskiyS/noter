package com.tmebot.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.uri.UriBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class BotClient {

  private final Logger logger = LoggerFactory.getLogger(BotClient.class);
  private final HttpClient httpClient;

  @Value("${telegram.token}")
  private String token;

  @Value("${ngrok.host}")
  private String ngrokHost;

  @Value("${ngrok.local}")
  private String ngrokLocal;


  public BotClient(@Client("${telegram.api}") HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  @PostConstruct
  private void setWebhook() throws JsonProcessingException {
    URI uri = UriBuilder.of("/bot" + token)
        .path("setWebhook")
        .queryParam("url", ngrokHost + "/" + token)
        .build();

    String response = httpClient.toBlocking().retrieve(HttpRequest.GET(uri));

    ObjectMapper mapper = new ObjectMapper();
    JsonNode json = mapper.readTree(response);

    if (json.get("ok").asBoolean()) {
      logger.info("Web hook initialized. Ngrok proxy {} -> {}", ngrokHost, ngrokLocal);
    } else {
      logger.error("Web hook initialized failed: {}", json.get("description").asText());
    }
  }

  public void sendMessage(Update update) {
    URI uri = UriBuilder.of("/bot" + token)
        .path("sendMessage")
        .build();

    try {
      httpClient.toBlocking().retrieve(HttpRequest.POST(uri, update));
      logger.info("Sending message: {}", update.toString());
    } catch (HttpClientResponseException e) {
      logger.error("status: {}, info: {}, response: {}", e.getStatus(), e.getMessage(), e.getResponse().body());
    }
  }
}
