package com.tmebot.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Handler {

  SendMessage messageHandler(Message message);

  SendMessage commandHandler(Message message);

  SendMessage callbackHandler(Update update);
}
