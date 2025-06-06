package com.nazarov.fluentplaywright.element;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.nazarov.fluentplaywright.conditions.element.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlaywrightElementTest {

  @Mock
  private Page page;

  @Mock
  private Locator locator;

  private PlaywrightElement element;
  private static final String SELECTOR = ".test-selector";

  @BeforeEach
  void setUp() {
    when(page.locator(SELECTOR)).thenReturn(locator);
    element = new PlaywrightElement(SELECTOR, page);
  }

  @Test
  void shouldCreateElementWithSelector() {
    assertThat(element.locator()).isEqualTo(locator);
  }

  @Test
  void shouldClickElement() {
    element.click();
    verify(locator).click();
  }

  @Test
  void shouldSetValue() {
    String value = "test value";
    element.setValue(value);
    verify(locator).fill(value);
  }

  @Test
  void shouldVerifyCondition() {
    Condition condition = mock(Condition.class);
    element.shouldBe(condition);
    verify(condition).verify(element);
  }

  @Test
  void shouldFindChildElement() {
    String childSelector = ".child";
    Locator childLocator = mock(Locator.class);
    when(locator.locator(childSelector)).thenReturn(childLocator);

    PlaywrightElement child = element.$(childSelector);
    assertThat(child).isNotNull();
    verify(locator).locator(childSelector);
  }

  @Test
  void shouldGetText() {
    String expectedText = "test text";
    when(locator.textContent()).thenReturn(expectedText);
    assertThat(element.getText()).isEqualTo(expectedText);
  }
}