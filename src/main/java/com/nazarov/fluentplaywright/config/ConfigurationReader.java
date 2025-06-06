package com.nazarov.fluentplaywright.config;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigurationReader {

  private static final String DEFAULT_CONFIG_FILE = "playwright.properties";

  private ConfigurationReader() {
  }

  public static PlaywrightConfig readConfiguration() {
    return readConfiguration(DEFAULT_CONFIG_FILE);
  }

  public static PlaywrightConfig readConfiguration(String configFile) {
    Properties properties = loadProperties(configFile);
    return buildConfig(properties);
  }

  private static Properties loadProperties(String configFile) {
    Properties properties = new Properties();

    try (InputStream input = ConfigurationReader.class.getClassLoader()
        .getResourceAsStream(configFile)) {
      if (input != null) {
        properties.load(input);
        log.info("Loaded configuration from: {}", configFile);
      } else {
        log.warn("Configuration file not found: {}. Using defaults.", configFile);
      }
    } catch (IOException e) {
      log.warn("Failed to load configuration file: {}. Using defaults.", configFile, e);
    }

    return properties;
  }

  private static PlaywrightConfig buildConfig(Properties properties) {
    return PlaywrightConfig.builder()
        .browserType(getBrowserType(properties))
        .headless(getBooleanProperty(properties, "headless", true))
        .assertionTimeout(getDurationProperty(properties, "assertionTimeout", Duration.ofSeconds(4)))
        .locatorTimeout(getDurationProperty(properties, "locatorTimeout", Duration.ofSeconds(4)))
        .waitForTimeout(getDurationProperty(properties, "waitForTimeout", Duration.ofSeconds(4)))
        .slowMo(getIntProperty(properties, "slow.mo", 0))
        .tracing(getBooleanProperty(properties, "tracing", false))
        .tracePath(getStringProperty(properties, "tracePath", ""))
        .recordVideo(getBooleanProperty(properties, "recordVideo", false))
        .videoPath(getStringProperty(properties, "videoPath", ""))
        .baseUrl(getStringProperty(properties, "base.url", ""))
        .build();
  }

  private static BrowserType getBrowserType(Properties properties) {
    String browserType = properties.getProperty("browser.type", "CHROMIUM").toUpperCase();
    try {
      return BrowserType.valueOf(browserType);
    } catch (IllegalArgumentException e) {
      log.warn("Invalid browser type: {}. Using CHROMIUM.", browserType);
      return BrowserType.CHROMIUM;
    }
  }

  private static boolean getBooleanProperty(Properties properties, String key,
      boolean defaultValue) {
    return Boolean.parseBoolean(properties.getProperty(key, String.valueOf(defaultValue)));
  }

  private static int getIntProperty(Properties properties, String key, int defaultValue) {
    try {
      return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
    } catch (NumberFormatException e) {
      log.warn("Invalid integer value for key: {}. Using default: {}", key, defaultValue);
      return defaultValue;
    }
  }

  private static Duration getDurationProperty(Properties properties, String key,
      Duration defaultValue) {
    try {
      return Duration.ofMillis(
          Long.parseLong(properties.getProperty(key, String.valueOf(defaultValue.toMillis()))));
    } catch (NumberFormatException e) {
      log.warn("Invalid duration value for key: {}. Using default: {}", key, defaultValue);
      return defaultValue;
    }
  }

  private static String getStringProperty(Properties properties, String key, String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }
}