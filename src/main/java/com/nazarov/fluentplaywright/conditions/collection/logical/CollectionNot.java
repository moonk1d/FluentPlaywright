package com.nazarov.fluentplaywright.conditions.collection.logical;

import com.nazarov.fluentplaywright.conditions.collection.CollectionCondition;
import com.nazarov.fluentplaywright.element.PlaywrightElementCollection;
import com.nazarov.fluentplaywright.exceptions.ConditionNotMetException;

public class CollectionNot implements CollectionCondition {
  private final CollectionCondition condition;

  public CollectionNot(CollectionCondition condition) {
    this.condition = condition;
  }

  @Override
  public void verify(PlaywrightElementCollection collection) {
    try {
      condition.verify(collection);
      throw new ConditionNotMetException("Condition that should not be met was met");
    } catch (ConditionNotMetException e) {
      // Expected - condition should not be met
    }
  }
}
