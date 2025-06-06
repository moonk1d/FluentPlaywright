package com.nazarov.fluentplaywright.config;

import java.time.Duration;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaywrightConfig {

  @Builder.Default
  private final BrowserType browserType = BrowserType.CHROMIUM;

  @Builder.Default
  private final boolean headless = true;

  @Builder.Default
  private final Duration locatorTimeout = Duration.ofSeconds(4);

  @Builder.Default
  private final Duration assertionTimeout = Duration.ofSeconds(4);

  @Builder.Default
  private final Duration waitForTimeout = Duration.ofSeconds(4);

  @Builder.Default
  private final int slowMo = 0;

  @Builder.Default
  private final boolean tracing = false;

  @Builder.Default
  private final String tracePath = "";

  @Builder.Default
  private final boolean recordVideo = false;

  @Builder.Default
  private final String videoPath = "";

  @Builder.Default
  private final String baseUrl = "";
}