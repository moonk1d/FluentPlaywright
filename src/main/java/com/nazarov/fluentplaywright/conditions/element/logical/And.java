package com.nazarov.fluentplaywright.conditions.element.logical;

import com.nazarov.fluentplaywright.conditions.element.Condition;
import com.nazarov.fluentplaywright.element.PlaywrightElement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class And implements Condition {
  private final Condition[] conditions;

  @Override
  public void verify(PlaywrightElement element) {
    for (Condition condition : conditions) {
      condition.verify(element);
    }
  }
}