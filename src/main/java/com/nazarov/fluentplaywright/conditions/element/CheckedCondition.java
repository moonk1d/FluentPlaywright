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
public class CheckedCondition implements Condition {
  @Builder.Default
  private final boolean checked = true;

  @Builder.Default
  private final Duration timeout = PlaywrightManager.getConfig().assertionTimeout();

  @Override
  public void verify(PlaywrightElement element) {
    LocatorAssertions.IsCheckedOptions options = new LocatorAssertions.IsCheckedOptions()
        .setTimeout(timeout.toMillis());

    if (checked) {
      assertThat(element.locator()).isChecked(options);
    } else {
      assertThat(element.locator()).isChecked(options.setChecked(false));
    }
  }
}
