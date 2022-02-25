package com.tmebot.telegram;

import com.tmebot.exception.NotMessageException;
import com.tmebot.service.MessageService;
import com.tmebot.telegram.model.Update;
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

  @Inject
  private MessageService messageService;

  @Post
  @Produces(MediaType.APPLICATION_JSON)
  public void telegramListener(@Body String request) {
    logger.info("Telegram request: {}", request);

    try {
      Update update = Update.fromString(request);
      messageService.handleUpdate(update);
    } catch (NotMessageException e) {
      logger.error("{}: {}", e.getMessage(), request);
    }
  }
}
