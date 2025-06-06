package com.nazarov.fluentplaywright.manager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.microsoft.playwright.Page;
import com.nazarov.fluentplaywright.config.PlaywrightConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlaywrightManagerTest {

  @Mock
  private PlaywrightConfig config;

  @Mock
  private Page page;

  @BeforeEach
  void setUp() {
    PlaywrightManager.resetConfig();
  }

  @AfterEach
  void tearDown() {
    PlaywrightManager.closeSession();
  }

  @Test
  void shouldInitializeWithConfig() {
    PlaywrightManager.initialize(config);
    assertThat(PlaywrightManager.getConfig()).isEqualTo(config);
  }

  @Test
  void shouldThrowExceptionWhenInitializingWithNullConfig() {
    assertThatThrownBy(() -> PlaywrightManager.initialize(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Configuration cannot be null");
  }

  @Test
  void shouldSetAndGetCurrentPage() {
    PlaywrightManager.initialize(config);
    PlaywrightManager.setCurrentPage(page);
    assertThat(PlaywrightManager.getPage()).isEqualTo(page);
  }

  @Test
  void shouldSwitchBetweenPages() {
    PlaywrightManager.initialize(config);
    Page page1 = mock(Page.class);
    Page page2 = mock(Page.class);

    PlaywrightManager.setCurrentPage(page1);
    assertThat(PlaywrightManager.getPage()).isEqualTo(page1);

    PlaywrightManager.switchToPage(page2);
    assertThat(PlaywrightManager.getPage()).isEqualTo(page2);
  }

  @Test
  void shouldInitializeWithDefaultConfig() {
    PlaywrightManager.initialize();
    PlaywrightConfig defaultConfig = PlaywrightManager.getConfig();

    assertThat(defaultConfig).isNotNull();
  }

  @Test
  void shouldReinitializeAfterReset() {
    PlaywrightManager.initialize(config);
    PlaywrightManager.resetConfig();

    assertThat(PlaywrightManager.getConfig()).isNotNull();
    assertThat(PlaywrightManager.getConfig()).isNotEqualTo(config);
  }
}