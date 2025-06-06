package com.nazarov.fluentplaywright.conditions.collection;

import com.nazarov.fluentplaywright.element.PlaywrightElementCollection;
import com.nazarov.fluentplaywright.exceptions.ConditionNotMetException;
import com.nazarov.fluentplaywright.manager.PlaywrightManager;
import java.time.Duration;
import java.util.List;
import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class CollectionTextCondition implements CollectionCondition {
  private final List<String> expectedTexts;

  @Builder.Default
  private final Duration timeout = PlaywrightManager.getConfig().assertionTimeout();

  @Builder.Default
  private final boolean useContains = false;

  @Builder.Default
  private final boolean ignoreCase = false;

  @Override
  public void verify(PlaywrightElementCollection collection) {
    List<String> actualTexts = collection.texts();

    if (!useContains) {
      // Exact match
      if (actualTexts.size() != expectedTexts.size()) {
        throw new ConditionNotMetException(
            String.format("Expected %d texts but found %d",
                expectedTexts.size(), actualTexts.size()));
      }

      for (int i = 0; i < expectedTexts.size(); i++) {
        String expected = ignoreCase ? expectedTexts.get(i).toLowerCase() : expectedTexts.get(i);
        String actual = ignoreCase ? actualTexts.get(i).toLowerCase() : actualTexts.get(i);

        if (!expected.equals(actual)) {
          throw new ConditionNotMetException(
              String.format("Text mismatch at index %d. Expected: '%s', but got: '%s'",
                  i, expectedTexts.get(i), actualTexts.get(i)));
        }
      }
    } else {
      // Contains
      for (String expectedText : expectedTexts) {
        String searchText = ignoreCase ? expectedText.toLowerCase() : expectedText;
        boolean found = actualTexts.stream()
            .map(text -> ignoreCase ? text.toLowerCase() : text)
            .anyMatch(text -> text.contains(searchText));

        if (!found) {
          throw new ConditionNotMetException(
              String.format("Text '%s' not found in collection", expectedText));
        }
      }
    }
  }
}