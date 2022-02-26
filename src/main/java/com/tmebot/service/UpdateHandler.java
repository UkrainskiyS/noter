package com.tmebot.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.tmebot.config.BotConfig;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;


@Singleton
public record UpdateHandler(@Inject BotConfig config) implements Handler {

  public SendMessage messageHandler(Message message) {
    // TODO
    return null;
  }

  public SendMessage commandHandler(Message message) {
    // TODO
    return null;
  }

  public SendMessage callbackHandler(Update update) {
    // TODO
    return null;
  }
}
