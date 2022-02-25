package com.tmebot;

import com.pengrad.telegrambot.TelegramBot;
import com.tmebot.telegram.TelegramListener;
import io.micronaut.runtime.Micronaut;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Application {

  public static void main(String[] args) {

    Micronaut.build(args)
        .eagerInitSingletons(true)
        .mainClass(Application.class)
        .start();
  }
}

