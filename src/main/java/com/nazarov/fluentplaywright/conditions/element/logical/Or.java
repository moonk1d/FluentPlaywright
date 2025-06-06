package com.nazarov.fluentplaywright.conditions.element.logical;

import com.nazarov.fluentplaywright.conditions.element.Condition;
import com.nazarov.fluentplaywright.element.PlaywrightElement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Or implements Condition {
  private final Condition[] conditions;

  @Override
  public void verify(PlaywrightElement element) {
    Exception lastException = null;
    for (Condition condition : conditions) {
      try {
        condition.verify(element);
        return;
      } catch (Exception e) {
        lastException = e;
      }
    }
    throw new RuntimeException("None of the conditions were met", lastException);
  }
}