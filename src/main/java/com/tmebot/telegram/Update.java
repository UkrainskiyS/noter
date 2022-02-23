package com.tmebot.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Serdeable.Serializable
public class Update {

  @JsonProperty("chat_id")
  private long chatId;

  @JsonProperty("text")
  private String text;

  public static Update decodeUpdate(ObjectMapper mapper, String update) throws JsonProcessingException {
    JsonNode json = mapper.readTree(update).get("message");
    return Update.builder()
        .chatId(json.get("chat").get("id").asLong())
        .text(json.get("text").asText())
        .build();
  }
}
