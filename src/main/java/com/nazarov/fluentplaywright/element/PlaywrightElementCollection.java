package com.nazarov.fluentplaywright.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.nazarov.fluentplaywright.conditions.collection.CollectionCondition;
import com.nazarov.fluentplaywright.manager.PlaywrightManager;
import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlaywrightElementCollection {

  @Getter
  private final Locator locator;
  private final Page page;
  private final String selector;

  public PlaywrightElementCollection(String selector) {
    this(selector, PlaywrightManager.getPage());
  }

  public PlaywrightElementCollection(String selector, Page page) {
    this.selector = selector;
    this.page = page;
    this.locator = page.locator(selector);
    log.debug("Created element collection with selector: {}", selector);
  }

  public PlaywrightElementCollection(Locator locator, Page page) {
    this.locator = locator;
    this.page = page;
    this.selector = locator.toString();
    log.debug("Created element collection with locator: {}", selector);
  }

  public PlaywrightElement $(String childSelector) {
    log.debug("Finding first child element '{}' within collection '{}'", childSelector, selector);
    return new PlaywrightElement(locator.locator(childSelector).first(), page);
  }

  public PlaywrightElementCollection $$(String childSelector) {
    log.debug("Finding child elements '{}' within collection '{}'", childSelector, selector);
    return new PlaywrightElementCollection(locator.locator(childSelector), page);
  }

  public PlaywrightElementCollection shouldBe(CollectionCondition... conditions) {
    for (CollectionCondition condition : conditions) {
      log.debug("Verifying condition {} for collection {}", condition, selector);
      condition.verify(this);
    }
    return this;
  }

  public PlaywrightElementCollection shouldHave(CollectionCondition... conditions) {
    return shouldBe(conditions);
  }

  public PlaywrightElement get(int index) {
    log.debug("Getting element at index {} from collection: {}", index, selector);
    return new PlaywrightElement(locator.nth(index), page);
  }

  public PlaywrightElement first() {
    log.debug("Getting first element from collection: {}", selector);
    return new PlaywrightElement(locator.first(), page);
  }

  public PlaywrightElement last() {
    log.debug("Getting last element from collection: {}", selector);
    return new PlaywrightElement(locator.last(), page);
  }

  public int size() {
    int count = locator.count();
    log.debug("Getting size of collection {}: {}", selector, count);
    return count;
  }

  public List<String> texts() {
    log.debug("Getting texts from collection: {}", selector);
    return IntStream.range(0, size())
        .mapToObj(i -> get(i).getText())
        .toList();
  }

  public List<String> values() {
    log.debug("Getting values from collection: {}", selector);
    return IntStream.range(0, size())
        .mapToObj(i -> get(i).getValue())
        .toList();
  }

  public PlaywrightElementCollection filter(String text) {
    log.debug("Filtering collection {} by text: {}", selector, text);
    return new PlaywrightElementCollection(locator.filter(new Locator.FilterOptions().setHasText(text)), page);
  }

  public PlaywrightElementCollection filterByText(String text) {
    return filter(text);
  }

  public PlaywrightElementCollection filterByAttribute(String name, String value) {
    log.debug("Filtering collection {} by attribute {}={}", selector, name, value);
    return new PlaywrightElementCollection(
        locator.filter(new Locator.FilterOptions().setHas(
            page.locator(String.format("[%s=\"%s\"]", name, value))
        )),
        page
    );
  }

  public PlaywrightElementCollection waitFor() {
    return waitFor(PlaywrightManager.getConfig().waitForTimeout());
  }

  public PlaywrightElementCollection waitFor(Duration timeout) {
    log.debug("Waiting for collection {} (timeout: {}ms)", selector, timeout.toMillis());
    locator.first().waitFor(new Locator.WaitForOptions().setTimeout(timeout.toMillis()));
    return this;
  }

  public boolean isEmpty() {
    return size() == 0;
  }
}