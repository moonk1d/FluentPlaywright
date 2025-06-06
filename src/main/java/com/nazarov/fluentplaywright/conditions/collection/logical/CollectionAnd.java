package com.nazarov.fluentplaywright.conditions.collection.logical;

import com.nazarov.fluentplaywright.conditions.collection.CollectionCondition;
import com.nazarov.fluentplaywright.element.PlaywrightElementCollection;
import com.nazarov.fluentplaywright.exceptions.ConditionNotMetException;

public class CollectionAnd implements CollectionCondition {
  private final CollectionCondition[] conditions;

  public CollectionAnd(CollectionCondition... conditions) {
    this.conditions = conditions;
  }

  @Override
  public void verify(PlaywrightElementCollection collection) {
    for (CollectionCondition condition : conditions) {
      condition.verify(collection);
    }
  }
}