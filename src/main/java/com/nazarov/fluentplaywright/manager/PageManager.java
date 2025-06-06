package com.nazarov.fluentplaywright.manager;

import com.microsoft.playwright.Page;
import java.util.List;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class PageManager {

  private PageManager () {
  }

  public static Page getCurrentPage() {
    return PlaywrightManager.getPage();
  }

  public static Page createNewPage() {
    return PlaywrightManager.createNewPage();
  }

  public static void switchToPage(Page page) {
    PlaywrightManager.setCurrentPage(page);
  }

  public static Page waitForPopup(Runnable action) {
    Page currentPage = getCurrentPage();
    Page[] popup = new Page[1];

    currentPage.context().onPage(page -> popup[0] = page);
    action.run();

    Page newPage = popup[0];
    if (newPage != null) {
      switchToPage(newPage);
    }
    return newPage;
  }

  public static Page switchToPageByUrl(String urlPattern) {
    List<Page> pages = PlaywrightManager.getAllPages();
    Page matchingPage = pages.stream()
        .filter(page -> page.url().matches(urlPattern))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("No page matching URL pattern: " + urlPattern));

    switchToPage(matchingPage);
    return matchingPage;
  }

  public static Page switchToPageByTitle(String titlePattern) {
    List<Page> pages = PlaywrightManager.getAllPages();
    Page matchingPage = pages.stream()
        .filter(page -> page.title().matches(titlePattern))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("No page matching title pattern: " + titlePattern));

    switchToPage(matchingPage);
    return matchingPage;
  }

  public static Page switchToPageByPredicate(Predicate<Page> predicate) {
    List<Page> pages = PlaywrightManager.getAllPages();
    Page matchingPage = pages.stream()
        .filter(predicate)
        .findFirst()
        .orElseThrow(() -> new RuntimeException("No page matching predicate"));

    switchToPage(matchingPage);
    return matchingPage;
  }
}