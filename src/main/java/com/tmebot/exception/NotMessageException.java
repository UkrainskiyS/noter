package com.tmebot.exception;

public class NotMessageException extends Exception {

  public NotMessageException() {
    super("Message not found in request");
  }
}
