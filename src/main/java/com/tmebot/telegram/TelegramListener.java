package com.tmebot.telegram;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetWebhook;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.tmebot.config.BotConfig;
import com.tmebot.service.UpdateHandler;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/${telegram.token}")
public class TelegramListener {

  private final Logger logger = LoggerFactory.getLogger(TelegramListener.class);

  private TelegramBot bot;

  @Inject
  private BotConfig config;

  @Inject
  private UpdateHandler handler;


  @PostConstruct
  private void init() {
    bot = new TelegramBot(config.token());
    BaseResponse response = bot.execute(new SetWebhook().url(config.proxy()));
    if (response.isOk()) {
      logger.info("Web hook initialized. Ngrok proxy {} -> {}", config.host(), config.local());
    } else {
      logger.error("Web hook initialized failed: {}", response.description());
      System.exit(1);
    }
  }

  @Post
  @Produces(MediaType.APPLICATION_JSON)
  public void telegramListener(@Body String request) {
    Update update = BotUtils.parseUpdate(request);

    if (update.message() != null && update.message().text() != null) {
      if (config.commands().contains(update.message().text())) {
        send(handler.commandHandler(update.message()));
      } else {
        send(handler.messageHandler(update.message()));
      }
    } else if (update.callbackQuery() != null) {
      send(handler.callbackHandler(update));
    }
  }

  private void send(SendMessage message) {
    bot.execute(message, new Callback<SendMessage, SendResponse>() {
      @Override
      public void onResponse(SendMessage request, SendResponse response) {
        logger.info("Message sending: " + response.message());
      }

      @Override
      public void onFailure(SendMessage request, IOException e) {
        logger.warn("Failure sending: " + e.getMessage());
      }
    });
  }
}
