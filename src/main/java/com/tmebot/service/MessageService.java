package com.tmebot.service;

import com.tmebot.telegram.BotClient;
import com.tmebot.telegram.Update;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MessageService {

  private final Logger logger = LoggerFactory.getLogger(MessageService.class);
  private final BotClient botClient;

  @Inject
  public MessageService(BotClient botClient) {
    this.botClient = botClient;
  }

  public void handleUpdate(Update update) {
    botClient.sendMessage(update);
  }
}
