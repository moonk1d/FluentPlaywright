package com.nazarov.fluentplaywright.manager;

import com.microsoft.playwright.Page;
import com.nazarov.fluentplaywright.config.ConfigurationReader;
import com.nazarov.fluentplaywright.config.PlaywrightConfig;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlaywrightManager {

  private static final ThreadLocal<SessionContext> sessionContext = new ThreadLocal<>();
  private static final ThreadLocal<Page> currentPage = new ThreadLocal<>();
  private static final ThreadLocal<PlaywrightConfig> config = new ThreadLocal<>();

  private PlaywrightManager() {
  }

  public static void initialize() {
    initialize(ConfigurationReader.readConfiguration());
  }

  public static void initialize(PlaywrightConfig configuration) {
    if (configuration == null) {
      throw new IllegalArgumentException("Configuration cannot be null");
    }
    config.set(configuration);
    log.debug("Initialized with configuration: {}", configuration);
  }

  public static Page getPage() {
    return currentPage.get() != null ? currentPage.get() : getOrCreateSession().page();
  }

  public static void setCurrentPage(Page page) {
    if (page == null) {
      throw new IllegalArgumentException("Page cannot be null");
    }
    currentPage.set(page);
    log.debug("Set page: {}", page);
  }

  public static void resetCurrentPage() {
    currentPage.remove();
  }

  public static Page createNewPage() {
    Page newPage = getOrCreateSession().browserContext().newPage();
    setCurrentPage(newPage);
    return newPage;
  }

  public static void switchToPage(Page page) {
    setCurrentPage(page);
  }

  public static List<Page> getAllPages() {
    return getOrCreateSession().browserContext().pages();
  }

  public static PlaywrightConfig getConfig() {
    if (config.get() == null) {
      synchronized (PlaywrightManager.class) {
        if (config.get() == null) {
          initialize();
        }
      }
    }
    return config.get();
  }

  private static SessionContext getOrCreateSession() {
    SessionContext context = sessionContext.get();
    if (context == null) {
      synchronized (PlaywrightManager.class) {
        context = sessionContext.get();
        if (context == null) {
          log.debug("Creating new session context");
          context = new SessionContext(getConfig());
          sessionContext.set(context);
        }
      }
    }
    return context;
  }

  public static void closeSession() {
    resetCurrentPage();
    SessionContext context = sessionContext.get();
    if (context != null) {
      try {
        context.close();
      } finally {
        sessionContext.remove();
        log.debug("Session context closed and removed");
      }
    }
  }

  public static void resetConfig() {
    config.remove();
  }
}