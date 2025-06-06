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
public class StateCondition implements Condition {
  public enum State {
    ENABLED, DISABLED, EDITABLE, FOCUSED, VISIBLE, HIDDEN, ATTACHED
  }

  private final State state;

  @Builder.Default
  private final Duration timeout = PlaywrightManager.getConfig().assertionTimeout();

  @Override
  public void verify(PlaywrightElement element) {
    switch (state) {
      case ENABLED -> assertThat(element.locator())
          .isEnabled(new LocatorAssertions.IsEnabledOptions().setTimeout(timeout.toMillis()));
      case DISABLED -> assertThat(element.locator())
          .isDisabled(new LocatorAssertions.IsDisabledOptions().setTimeout(timeout.toMillis()));
      case EDITABLE -> assertThat(element.locator())
          .isEditable(new LocatorAssertions.IsEditableOptions().setTimeout(timeout.toMillis()));
      case FOCUSED -> assertThat(element.locator())
          .isFocused(new LocatorAssertions.IsFocusedOptions().setTimeout(timeout.toMillis()));
      case VISIBLE -> assertThat(element.locator())
          .isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(timeout.toMillis()));
      case HIDDEN -> assertThat(element.locator())
          .isHidden(new LocatorAssertions.IsHiddenOptions().setTimeout(timeout.toMillis()));
      case ATTACHED -> assertThat(element.locator())
          .isAttached(new LocatorAssertions.IsAttachedOptions().setTimeout(timeout.toMillis()));

      default -> throw new IllegalStateException("Unexpected state: " + state);
    }
  }
}