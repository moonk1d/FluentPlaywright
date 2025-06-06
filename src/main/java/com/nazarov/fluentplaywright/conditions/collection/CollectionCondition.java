package com.nazarov.fluentplaywright.conditions.collection;

import com.nazarov.fluentplaywright.element.PlaywrightElementCollection;

@FunctionalInterface
public interface CollectionCondition {
  void verify(PlaywrightElementCollection collection);
}