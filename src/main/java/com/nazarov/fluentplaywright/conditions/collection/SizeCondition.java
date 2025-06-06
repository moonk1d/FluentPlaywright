package com.nazarov.fluentplaywright.conditions.collection;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.assertions.LocatorAssertions;
import com.nazarov.fluentplaywright.element.PlaywrightElementCollection;
import com.nazarov.fluentplaywright.exceptions.ConditionNotMetException;
import com.nazarov.fluentplaywright.manager.PlaywrightManager;
import java.time.Duration;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class SizeCondition implements CollectionCondition {
  public enum Comparison {
    EQUAL, GREATER_THAN, LESS_THAN, BETWEEN
  }

  @Builder.Default
  private final Duration timeout = PlaywrightManager.getConfig().assertionTimeout();

  @Builder.Default
  private final Comparison comparison = Comparison.EQUAL;

  private final Integer expectedSize;
  private final Integer minSize;
  private final Integer maxSize;

  @Override
  public void verify(PlaywrightElementCollection collection) {
    LocatorAssertions.HasCountOptions options = new LocatorAssertions.HasCountOptions()
        .setTimeout(timeout.toMillis());

    int actualSize = collection.size();

    switch (comparison) {
      case EQUAL -> assertThat(collection.locator()).hasCount(expectedSize, options);
      case GREATER_THAN -> {
        if (actualSize <= expectedSize) {
          throw new ConditionNotMetException(
              String.format("Expected size to be greater than %d but was %d",
                  expectedSize, actualSize));
        }
      }
      case LESS_THAN -> {
        if (actualSize >= expectedSize) {
          throw new ConditionNotMetException(
              String.format("Expected size to be less than %d but was %d",
                  expectedSize, actualSize));
        }
      }
      case BETWEEN -> {
        if (actualSize < minSize || actualSize > maxSize) {
          throw new ConditionNotMetException(
              String.format("Expected size to be between %d and %d but was %d",
                  minSize, maxSize, actualSize));
        }
      }
    }
  }
}