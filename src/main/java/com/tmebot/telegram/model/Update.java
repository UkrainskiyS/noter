package com.tmebot.telegram.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmebot.exception.NotMessageException;
import fm.finch.json.json.Json;
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

  public static Update fromString(String request) throws NotMessageException {
    Json json = Json.parse(request).get("message");
    if (json == null) {
      throw new NotMessageException();
    }

    return Update.builder()
        .chatId(json.get("chat").get("id").asLong(0))
        .text(json.get("text").asString())
        .build();
  }
}
