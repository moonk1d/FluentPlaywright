package com.nazarov.fluentplaywright.exceptions;

public class PlaywrightWrapperException extends RuntimeException {
  public PlaywrightWrapperException(String message) {
    super(message);
  }

  public PlaywrightWrapperException(String message, Throwable cause) {
    super(message, cause);
  }
}