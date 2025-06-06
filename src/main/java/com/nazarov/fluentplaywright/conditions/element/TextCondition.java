package com.nazarov.fluentplaywright.conditions.element;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.assertions.LocatorAssertions;
import com.nazarov.fluentplaywright.element.PlaywrightElement;
import com.nazarov.fluentplaywright.manager.PlaywrightManager;
import java.time.Duration;
import lombok.Builder;

@Builder
public class TextCondition implements Condition {

  private final String expectedText;

  @Builder.Default
  private final Duration timeout = PlaywrightManager.getConfig().assertionTimeout();

  @Builder.Default
  private final boolean ignoreCase = false;

  @Builder.Default
  private final boolean useContains = false;

  @Override
  public void verify(PlaywrightElement element) {
    String textToCheck = ignoreCase ? expectedText.toLowerCase() : expectedText;

    LocatorAssertions.HasTextOptions hasTextOptions = new LocatorAssertions.HasTextOptions()
        .setTimeout(timeout.toMillis());

    LocatorAssertions.ContainsTextOptions containsTextOptions = new LocatorAssertions.ContainsTextOptions()
        .setTimeout(timeout.toMillis());

    if (useContains) {
      assertThat(element.locator()).containsText(textToCheck, containsTextOptions);
    } else {
      assertThat(element.locator()).hasText(textToCheck, hasTextOptions);
    }
  }
}

