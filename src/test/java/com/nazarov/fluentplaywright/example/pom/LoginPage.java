package com.nazarov.fluentplaywright.example.pom;

import static com.nazarov.fluentplaywright.FluentPlaywright.$;
import static com.nazarov.fluentplaywright.conditions.element.Conditions.enabled;
import static com.nazarov.fluentplaywright.conditions.element.Conditions.exactText;
import static com.nazarov.fluentplaywright.conditions.element.Conditions.visible;

import com.nazarov.fluentplaywright.element.PlaywrightElement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginPage extends BasePage {
  // Locators
  private final PlaywrightElement usernameInput = $("#user-name");
  private final PlaywrightElement passwordInput = $("#password");
  private final PlaywrightElement loginButton = $("#login-button");
  private final PlaywrightElement errorMessage = $("[data-test='error']");

  @Override
  protected PlaywrightElement getPageIdentifier() {
    return loginButton;
  }

  public LoginPage enterUsername(String username) {
    log.info("Entering username: {}", username);
    usernameInput
        .shouldBe(visible())
        .setValue(username);
    return this;
  }

  public LoginPage enterPassword(String password) {
    log.info("Entering password: {}", password);
    passwordInput
        .shouldBe(visible())
        .setValue(password);
    return this;
  }

  public InventoryPage clickLoginButton() {
    log.info("Clicking login button");
    loginButton
        .shouldBe(visible(), enabled())
        .click();
    return new InventoryPage();
  }

  public LoginPage verifyErrorMessage(String expectedError) {
    log.info("Verifying error message: {}", expectedError);
    errorMessage
        .shouldBe(visible())
        .shouldHave(exactText(expectedError));
    return this;
  }

  public InventoryPage login(String username, String password) {
    log.info("Logging in with username: {}", username);
    return enterUsername(username)
        .enterPassword(password)
        .clickLoginButton();
  }
}