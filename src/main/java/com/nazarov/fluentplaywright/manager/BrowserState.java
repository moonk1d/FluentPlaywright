package com.nazarov.fluentplaywright.manager;

import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class BrowserState {

  private BrowserState() {
  }

  public static void clearBrowserData() {
    Page page = PlaywrightManager.getPage();
    log.debug("Clearing browser data");
    page.evaluate("window.localStorage.clear();");
    page.evaluate("window.sessionStorage.clear();");
    clearCookies();
  }

  public static void clearCookies() {
    log.debug("Clearing cookies");
    PlaywrightManager.getPage().context().clearCookies();
  }

  public static void refresh() {
    log.debug("Refreshing page");
    PlaywrightManager.getPage().reload();
  }

  public static void navigateTo(String url) {
    log.debug("Navigating to: {}", url);
    PlaywrightManager.getPage().navigate(url);
  }
}