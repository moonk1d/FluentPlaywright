package com.nazarov.fluentplaywright.conditions.collection;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.assertions.LocatorAssertions;
import com.nazarov.fluentplaywright.element.PlaywrightElementCollection;
import com.nazarov.fluentplaywright.manager.PlaywrightManager;
import java.time.Duration;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class CollectionAttributeCondition implements CollectionCondition {
  private final String name;
  private final String value;

  @Builder.Default
  private final Duration timeout = PlaywrightManager.getConfig().assertionTimeout();

  @Override
  public void verify(PlaywrightElementCollection collection) {
    LocatorAssertions.HasAttributeOptions options = new LocatorAssertions.HasAttributeOptions()
        .setTimeout(timeout.toMillis());

    for (int i = 0; i < collection.size(); i++) {
      assertThat(collection.locator().nth(i)).hasAttribute(name, value, options);
    }
  }
}