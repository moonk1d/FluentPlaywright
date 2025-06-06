package com.nazarov.fluentplaywright.example.pom;

import static com.nazarov.fluentplaywright.conditions.element.Conditions.visible;

import com.nazarov.fluentplaywright.element.PlaywrightElement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BasePage {

  protected abstract PlaywrightElement getPageIdentifier();

  public void verifyPageLoaded() {
    log.info("Verifying {} is loaded", this.getClass().getSimpleName());
    getPageIdentifier().shouldBe(visible());
  }
}