package com.nazarov.fluentplaywright.conditions.collection;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.assertions.LocatorAssertions;
import com.nazarov.fluentplaywright.conditions.element.StateCondition;
import com.nazarov.fluentplaywright.element.PlaywrightElementCollection;
import com.nazarov.fluentplaywright.manager.PlaywrightManager;
import java.time.Duration;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class CollectionStateCondition implements CollectionCondition {
  private final StateCondition.State state;

  @Builder.Default
  private final Duration timeout = PlaywrightManager.getConfig().assertionTimeout();

  @Override
  public void verify(PlaywrightElementCollection collection) {
    for (int i = 0; i < collection.size(); i++) {
      switch (state) {
        case VISIBLE -> assertThat(collection.locator().nth(i))
            .isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(timeout.toMillis()));
        case ENABLED -> assertThat(collection.locator().nth(i))
            .isEnabled(new LocatorAssertions.IsEnabledOptions().setTimeout(timeout.toMillis()));
      }
    }
  }
}