package com.nazarov.fluentplaywright.example;

import com.nazarov.fluentplaywright.config.ConfigurationReader;
import com.nazarov.fluentplaywright.config.PlaywrightConfig;
import com.nazarov.fluentplaywright.manager.PlaywrightManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestFixture {

  private static PlaywrightConfig config;

  @BeforeAll
  static void setUpConfig() {
    config = ConfigurationReader.readConfiguration();
  }

  @BeforeEach
  void setUp() {
    PlaywrightManager.initialize(config);
  }

  @AfterEach
  void tearDown() {
    PlaywrightManager.closeSession();
  }

}
