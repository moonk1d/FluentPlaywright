package com.nazarov.fluentplaywright.manager;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.nazarov.fluentplaywright.config.PlaywrightConfig;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class SessionContext implements AutoCloseable {

  private final Playwright playwright;
  private final Browser browser;
  private final BrowserContext browserContext;
  private final Page page;
  private final PlaywrightConfig config;

  public SessionContext(PlaywrightConfig config) {
    this.config = config;
    this.playwright = createPlaywright();
    this.browser = createBrowser();
    this.browserContext = createBrowserContext();
    this.page = createPage();
  }

  private Playwright createPlaywright() {
    log.debug("Creating Playwright instance");
    return Playwright.create();
  }

  private Browser createBrowser() {
    log.debug("Creating Browser instance with type: {}", config.browserType());
    BrowserType browserType = switch (config.browserType()) {
      case FIREFOX -> playwright.firefox();
      case WEBKIT -> playwright.webkit();
      default -> playwright.chromium();
    };

    return browserType.launch(new BrowserType.LaunchOptions()
        .setHeadless(config.headless())
        .setSlowMo(config.slowMo()));
  }

  private BrowserContext createBrowserContext() {
    log.debug("Creating Browser Context");
    Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
        .setBaseURL(config.baseUrl())
        .setRecordVideoDir(config.recordVideo() ? Path.of(config.videoPath()) : null);

    BrowserContext context = browser.newContext(contextOptions);
    if (config.tracing()) {
      log.debug("Starting tracing for Browser Context");
      context.tracing().start(new Tracing.StartOptions()
          .setScreenshots(true)
          .setSources(true)
          .setSnapshots(true));
    }

    return context;
  }

  private Page createPage() {
    log.debug("Creating new Page");
    Page page = browserContext.newPage();
    log.debug("Configuring locator timeout to: {} ms", config.locatorTimeout().toMillis());
    page.setDefaultTimeout(config.locatorTimeout().toMillis());
    return page;
  }

  @Override
  public void close() {
    try {
      if (config.tracing()) {
        log.debug("Stopping tracing for Browser Context");
        browserContext.tracing().stop(new Tracing.StopOptions()
            .setPath(Paths.get(
                String.format("%s/trace-%d.zip", config.tracePath(), System.currentTimeMillis()))));
      }
      if (page != null) {
        log.debug("Closing Page");
        page.close();
      }
      if (browserContext != null) {
        log.debug("Closing Browser Context");
        browserContext.close();
      }
      if (browser != null) {
        log.debug("Closing Browser");
        browser.close();
      }
      if (playwright != null) {
        log.debug("Closing Playwright");
        playwright.close();
      }
    } catch (Exception e) {
      log.error("Error while closing session resources", e);
    }
  }
}