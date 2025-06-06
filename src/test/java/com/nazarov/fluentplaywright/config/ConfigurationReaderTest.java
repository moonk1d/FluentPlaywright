package com.nazarov.fluentplaywright.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.junit.jupiter.api.Test;

class ConfigurationReaderTest {

  @Test
  void shouldReadDefaultConfiguration() {
    PlaywrightConfig config = ConfigurationReader.readConfiguration();

    assertThat(config.browserType()).isEqualTo(BrowserType.CHROMIUM);
    assertThat(config.headless()).isTrue();
    assertThat(config.locatorTimeout()).isEqualTo(Duration.ofSeconds(4));
    assertThat(config.assertionTimeout()).isEqualTo(Duration.ofSeconds(4));
  }

  @Test
  void shouldReadCustomConfiguration() {
    PlaywrightConfig config = ConfigurationReader.readConfiguration("custom-playwright.properties");

    assertThat(config.browserType()).isEqualTo(BrowserType.FIREFOX);
    assertThat(config.headless()).isFalse();
    assertThat(config.locatorTimeout()).isEqualTo(Duration.ofSeconds(10));
  }
}