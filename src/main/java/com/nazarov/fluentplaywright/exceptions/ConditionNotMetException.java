package com.nazarov.fluentplaywright.exceptions;

public class ConditionNotMetException extends PlaywrightWrapperException {
  public ConditionNotMetException(String message) {
    super(message);
  }

  public ConditionNotMetException(String message, Throwable cause) {
    super(message, cause);
  }
}