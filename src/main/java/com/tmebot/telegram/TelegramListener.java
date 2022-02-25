package com.tmebot.telegram;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetWebhook;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/${telegram.token}")
public class TelegramListener {

  private final Logger logger = LoggerFactory.getLogger(TelegramListener.class);

  @Value("${telegram.token}")
  private String token;

  @Value("${ngrok.host}")
  private String ngrokHost;

  @Value("${ngrok.local}")
  private String ngrokLocal;

  private TelegramBot bot;

  @PostConstruct
  private void init() {
    bot = new TelegramBot(token);
    BaseResponse response = bot.execute(new SetWebhook().url(ngrokHost + "/" + token));
    if (response.isOk()) {
      logger.info("Web hook initialized. Ngrok proxy {} -> {}", ngrokHost, ngrokLocal);
    } else {
      logger.error("Web hook initialized failed: {}", response.description());
      System.exit(1);
    }
  }

  @Post
  @Produces(MediaType.APPLICATION_JSON)
  public void telegramListener(@Body String request) {
    Update update = BotUtils.parseUpdate(request);

    SendMessage message = new SendMessage(update.message().chat().id(), update.message().text());
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
