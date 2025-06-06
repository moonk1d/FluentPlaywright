package com.nazarov.fluentplaywright.example;

import static com.nazarov.fluentplaywright.manager.PlaywrightManager.getPage;

import com.nazarov.fluentplaywright.example.pom.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SauceDemoTest extends TestFixture {
  private LoginPage loginPage;

  @BeforeEach
  void setUpTest() {
    getPage().navigate("https://www.saucedemo.com");
    loginPage = new LoginPage();
    loginPage.verifyPageLoaded();
  }

  @Test
  void successfulPurchaseFlow() {
    loginPage
        .login("standard_user", "secret_sauce")
        .verifyPageTitle("Products")
        .addItemToCart("Sauce Labs Backpack")
        .addItemToCart("Sauce Labs Bike Light")
        .verifyItemCount(2)
        .openCart()
        .verifyCartItemsCount(2)
        .verifyItemInCart("Sauce Labs Backpack")
        .verifyItemInCart("Sauce Labs Bike Light")
        .removeItem("Sauce Labs Bike Light")
        .verifyCartItemsCount(1)
        .continueShopping()
        .verifyItemCount(1);
  }

  @Test
  void loginWithInvalidCredentials() {
    loginPage
        .enterUsername("invalid_user")
        .enterPassword("invalid_password")
        .clickLoginButton();

    loginPage.verifyErrorMessage(
        "Epic sadface: Username and password do not match any user in this service");
  }
}