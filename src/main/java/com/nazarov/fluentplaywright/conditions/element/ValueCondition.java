package com.nazarov.fluentplaywright.conditions.element;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.assertions.LocatorAssertions;
import com.nazarov.fluentplaywright.element.PlaywrightElement;
import com.nazarov.fluentplaywright.manager.PlaywrightManager;
import java.time.Duration;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class ValueCondition implements Condition {
  private final String expectedValue;

  @Builder.Default
  private final Duration timeout = PlaywrightManager.getConfig().assertionTimeout();

  @Override
  public void verify(PlaywrightElement element) {
    assertThat(element.locator()).hasValue(expectedValue,
        new LocatorAssertions.HasValueOptions().setTimeout(timeout.toMillis()));
  }
}
