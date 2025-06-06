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
public class CssPropertyCondition implements Condition {
  private final String property;
  private final String value;

  @Builder.Default
  private final Duration timeout = PlaywrightManager.getConfig().assertionTimeout();

  @Override
  public void verify(PlaywrightElement element) {
    assertThat(element.locator()).hasCSS(property, value,
        new LocatorAssertions.HasCSSOptions().setTimeout(timeout.toMillis()));
  }
}