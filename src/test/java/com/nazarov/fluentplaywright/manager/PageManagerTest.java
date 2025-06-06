package com.nazarov.fluentplaywright.manager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

class PageManagerTest {

  @Test
  void shouldGetCurrentPage() {
    try (MockedStatic<PlaywrightManager> mockedPlaywrightManager = mockStatic(PlaywrightManager.class)) {
      Page mockPage = mock(Page.class);
      mockedPlaywrightManager.when(PlaywrightManager::getPage).thenReturn(mockPage);

      Page currentPage = PageManager.getCurrentPage();
      assertThat(currentPage).isEqualTo(mockPage);
    }
  }

  @Test
  void shouldCreateNewPage() {
    try (MockedStatic<PlaywrightManager> mockedPlaywrightManager = mockStatic(PlaywrightManager.class)) {
      Page mockPage = mock(Page.class);
      mockedPlaywrightManager.when(PlaywrightManager::createNewPage).thenReturn(mockPage);

      Page newPage = PageManager.createNewPage();
      assertThat(newPage).isEqualTo(mockPage);
    }
  }

  @Test
  void shouldSwitchToPage() {
    try (MockedStatic<PlaywrightManager> mockedPlaywrightManager = mockStatic(PlaywrightManager.class)) {
      Page mockPage = mock(Page.class);

      PageManager.switchToPage(mockPage);
      mockedPlaywrightManager.verify(() -> PlaywrightManager.setCurrentPage(mockPage));
    }
  }

  @Test
  void shouldSwitchToPageByUrl() {
    try (MockedStatic<PlaywrightManager> mockedPlaywrightManager = mockStatic(PlaywrightManager.class)) {
      Page mockPage1 = mock(Page.class);
      Page mockPage2 = mock(Page.class);
      when(mockPage1.url()).thenReturn("https://example.com");
      when(mockPage2.url()).thenReturn("https://test.com");
      mockedPlaywrightManager.when(PlaywrightManager::getAllPages).thenReturn(List.of(mockPage1, mockPage2));

      PageManager.switchToPageByUrl("https://test\\.com");
      mockedPlaywrightManager.verify(() -> PlaywrightManager.setCurrentPage(mockPage2));
    }
  }

  @Test
  void shouldThrowExceptionWhenNoPageMatchesUrl() {
    try (MockedStatic<PlaywrightManager> mockedPlaywrightManager = mockStatic(PlaywrightManager.class)) {
      mockedPlaywrightManager.when(PlaywrightManager::getAllPages).thenReturn(List.of());

      assertThatThrownBy(() -> PageManager.switchToPageByUrl("https://nonexistent\\.com"))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining("No page matching URL pattern");
    }
  }

  @Test
  void shouldSwitchToPageByTitle() {
    try (MockedStatic<PlaywrightManager> mockedPlaywrightManager = mockStatic(PlaywrightManager.class)) {
      Page mockPage1 = mock(Page.class);
      Page mockPage2 = mock(Page.class);
      when(mockPage1.title()).thenReturn("Example");
      when(mockPage2.title()).thenReturn("Test");
      mockedPlaywrightManager.when(PlaywrightManager::getAllPages).thenReturn(List.of(mockPage1, mockPage2));

      PageManager.switchToPageByTitle("Test");
      mockedPlaywrightManager.verify(() -> PlaywrightManager.setCurrentPage(mockPage2));
    }
  }

  @Test
  void shouldSwitchToPageByPredicate() {
    try (MockedStatic<PlaywrightManager> mockedPlaywrightManager = mockStatic(PlaywrightManager.class)) {
      Page mockPage1 = mock(Page.class);
      Page mockPage2 = mock(Page.class);
      when(mockPage1.url()).thenReturn("https://example.com");
      when(mockPage2.url()).thenReturn("https://test.com");
      mockedPlaywrightManager.when(PlaywrightManager::getAllPages).thenReturn(List.of(mockPage1, mockPage2));

      PageManager.switchToPageByPredicate(page -> page.url().contains("test"));
      mockedPlaywrightManager.verify(() -> PlaywrightManager.setCurrentPage(mockPage2));
    }
  }
}