package com.tmebot.service;

import com.tmebot.telegram.BotClient;
import com.tmebot.telegram.model.Update;
import jakarta.inject.Singleton;

@Singleton
public record MessageService(BotClient botClient) {

  public void handleUpdate(Update update) {
    botClient.sendMessage(update);
  }
}
