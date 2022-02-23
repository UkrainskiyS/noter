package com.tmebot.telegram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmebot.service.MessageService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/${telegram.token}")
public class TelegramListener {

  private final Logger logger = LoggerFactory.getLogger(BotClient.class);
  private final ObjectMapper mapper = new ObjectMapper();

  @Inject
  private MessageService messageService;

  @Post
  @Produces(MediaType.APPLICATION_JSON)
  public void telegramListener(@Body String request) throws JsonProcessingException {
    logger.info("Telegram request: {}", request);

    Update update = Update.decodeUpdate(mapper, request);
    messageService.handleUpdate(update);
  }
}
