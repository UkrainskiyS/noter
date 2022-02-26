package com.tmebot.config;

import io.micronaut.context.annotation.Value;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class BotConfig {

  @Value("${ngrok.host}")
  private String host;

  @Value("${ngrok.local}")
  private String local;

  @Value("${telegram.token}")
  private String token;

  @Value("${telegram.name}")
  private String name;

  @Value("${telegram.commands}")
  private HashSet<String> commands;


  @PostConstruct
  private void init() {
    Logger logger = LoggerFactory.getLogger(BotConfig.class);
    commands.forEach(logger::info);
    commands.addAll(
        commands.stream()
            .map(it -> it + '@' + name)
            .collect(Collectors.toSet())
    );
  }

  public HashSet<String> commands() {
    return commands;
  }

  public String host() {
    return host;
  }

  public String local() {
    return local;
  }

  public String token() {
    return token;
  }

  public String proxy() {
    return host + "/" + token;
  }
}
