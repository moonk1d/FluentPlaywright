package com.nazarov.fluentplaywright.conditions.element;

import com.nazarov.fluentplaywright.element.PlaywrightElement;

@FunctionalInterface
public interface Condition {
  void verify(PlaywrightElement element);
}