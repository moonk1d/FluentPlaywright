package com.nazarov.fluentplaywright;

import com.nazarov.fluentplaywright.element.PlaywrightElement;
import com.nazarov.fluentplaywright.element.PlaywrightElementCollection;
import com.nazarov.fluentplaywright.manager.PageManager;

public class FluentPlaywright {

  private FluentPlaywright() {
  }

  public static PlaywrightElement $(String selector) {
    return new PlaywrightElement(selector, PageManager.getCurrentPage());
  }

  public static PlaywrightElementCollection $$(String selector) {
    return new PlaywrightElementCollection(selector, PageManager.getCurrentPage());
  }
}