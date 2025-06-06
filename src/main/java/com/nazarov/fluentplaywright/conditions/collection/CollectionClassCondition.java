package com.nazarov.fluentplaywright.conditions.collection;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.assertions.LocatorAssertions;
import com.nazarov.fluentplaywright.element.PlaywrightElementCollection;
import com.nazarov.fluentplaywright.manager.PlaywrightManager;
import java.time.Duration;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class CollectionClassCondition implements CollectionCondition {
  private final String className;

  @Builder.Default
  private final Duration timeout = PlaywrightManager.getConfig().assertionTimeout();

  @Override
  public void verify(PlaywrightElementCollection collection) {
    LocatorAssertions.HasClassOptions options = new LocatorAssertions.HasClassOptions()
        .setTimeout(timeout.toMillis());

    Pattern pattern = Pattern.compile("\\b" + Pattern.quote(className) + "\\b");

    for (int i = 0; i < collection.size(); i++) {
      assertThat(collection.locator().nth(i)).hasClass(pattern, options);
    }
  }
}