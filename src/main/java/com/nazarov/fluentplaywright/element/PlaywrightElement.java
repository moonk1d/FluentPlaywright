package com.nazarov.fluentplaywright.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.nazarov.fluentplaywright.conditions.element.Condition;
import com.nazarov.fluentplaywright.manager.PlaywrightManager;
import java.time.Duration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlaywrightElement {

  @Getter
  private final Locator locator;
  private final Page page;
  private final String selector;

  public PlaywrightElement(String selector) {
    this(selector, PlaywrightManager.getPage());
  }

  public PlaywrightElement(String selector, Page page) {
    this.selector = selector;
    this.page = page;
    this.locator = page.locator(selector);
    log.debug("Created element with selector: {}", selector);
  }

  public PlaywrightElement(Locator locator, Page page) {
    this.locator = locator;
    this.page = page;
    this.selector = locator.toString(); // Note: this might not give the exact selector
    log.debug("Created element with locator: {}", selector);
  }

  public PlaywrightElement $(String childSelector) {
    log.debug("Finding child element '{}' within '{}'", childSelector, selector);
    return new PlaywrightElement(locator.locator(childSelector), page);
  }

  public PlaywrightElementCollection $$(String childSelector) {
    log.debug("Finding child elements '{}' within '{}'", childSelector, selector);
    return new PlaywrightElementCollection(locator.locator(childSelector), page);
  }

  public PlaywrightElement shouldBe(Condition... conditions) {
    for (Condition condition : conditions) {
      log.debug("Verifying condition {} for element {}", condition, selector);
      condition.verify(this);
    }
    return this;
  }

  public PlaywrightElement shouldHave(Condition... conditions) {
    return shouldBe(conditions);
  }

  public PlaywrightElement click() {
    log.debug("Clicking element: {}", selector);
    locator.click();
    return this;
  }

  public PlaywrightElement setValue(String value) {
    log.debug("Setting value '{}' for element: {}", value, selector);
    locator.fill(value);
    return this;
  }

  public PlaywrightElement type(String text) {
    log.debug("Typing text '{}' into element: {}", text, selector);
    locator.type(text);
    return this;
  }

  public PlaywrightElement clear() {
    log.debug("Clearing element: {}", selector);
    locator.clear();
    return this;
  }

  public PlaywrightElement pressKey(String key) {
    log.debug("Pressing key '{}' in element: {}", key, selector);
    locator.press(key);
    return this;
  }

  public String getText() {
    String text = locator.textContent();
    log.debug("Getting text from element {}: '{}'", selector, text);
    return text;
  }

  public String getValue() {
    String value = locator.inputValue();
    log.debug("Getting value from element {}: '{}'", selector, value);
    return value;
  }

  public boolean isVisible() {
    return locator.isVisible();
  }

  public boolean isEnabled() {
    return locator.isEnabled();
  }

  public PlaywrightElement hover() {
    log.debug("Hovering over element: {}", selector);
    locator.hover();
    return this;
  }

  public PlaywrightElement waitFor() {
    return waitFor(PlaywrightManager.getConfig().waitForTimeout());
  }

  public PlaywrightElement waitFor(Duration timeout) {
    log.debug("Waiting for element {} to be visible (timeout: {}ms)", selector, timeout.toMillis());
    locator.waitFor(new Locator.WaitForOptions().setTimeout(timeout.toMillis()));
    return this;
  }
}