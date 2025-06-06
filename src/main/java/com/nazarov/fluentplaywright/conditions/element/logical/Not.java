package com.nazarov.fluentplaywright.conditions.element.logical;

import com.nazarov.fluentplaywright.conditions.element.Condition;
import com.nazarov.fluentplaywright.element.PlaywrightElement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Not implements Condition {
  private final Condition condition;

  @Override
  public void verify(PlaywrightElement element) {
    try {
      condition.verify(element);
      throw new RuntimeException("Condition that should not be met was met");
    } catch (Exception e) {
      // Expected - condition should not be met
    }
  }
}