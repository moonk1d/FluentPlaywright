package com.nazarov.fluentplaywright.example;

import static com.nazarov.fluentplaywright.FluentPlaywright.$;
import static com.nazarov.fluentplaywright.conditions.element.Conditions.enabled;
import static com.nazarov.fluentplaywright.conditions.element.Conditions.exactText;
import static com.nazarov.fluentplaywright.conditions.element.Conditions.visible;

import com.nazarov.fluentplaywright.manager.PlaywrightManager;
import org.junit.jupiter.api.Test;

class LoginTest extends TestFixture {

  @Test
  void successfulLogin() {
    // Navigate to login page
    PlaywrightManager.getPage().navigate("https://www.saucedemo.com");

    // Fill login form
    $("#user-name")
        .shouldBe(visible())
        .setValue("standard_user");

    $("#password")
        .shouldBe(visible())
        .setValue("secret_sauce");

    $("#login-button")
        .shouldBe(enabled())
        .click();

    // Verify successful login
    $(".title")
        .shouldBe(visible())
        .shouldHave(exactText("Products"));
  }

  @Test
  void failedLogin() {
    PlaywrightManager.getPage().navigate("https://www.saucedemo.com");

    $("#user-name").setValue("wronguser");
    $("#password").setValue("wrongpass");
    $("#login-button").click();

    $(".error-message-container")
        .shouldBe(visible())
        .shouldBe(
            exactText("Epic sadface: Username and password do not match any user in this service"));
  }
}