package com.nazarov.fluentplaywright.conditions.collection.logical;

import com.nazarov.fluentplaywright.conditions.collection.CollectionCondition;
import com.nazarov.fluentplaywright.element.PlaywrightElementCollection;
import com.nazarov.fluentplaywright.exceptions.ConditionNotMetException;

public class CollectionOr implements CollectionCondition {
  private final CollectionCondition[] conditions;

  public CollectionOr(CollectionCondition... conditions) {
    this.conditions = conditions;
  }

  @Override
  public void verify(PlaywrightElementCollection collection) {
    Exception lastException = null;
    for (CollectionCondition condition : conditions) {
      try {
        condition.verify(collection);
        return;
      } catch (Exception e) {
        lastException = e;
      }
    }
    throw new ConditionNotMetException("None of the conditions were met", lastException);
  }
}
