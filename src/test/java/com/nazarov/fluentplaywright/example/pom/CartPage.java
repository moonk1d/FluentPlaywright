package com.nazarov.fluentplaywright.example.pom;

import static com.nazarov.fluentplaywright.FluentPlaywright.$;
import static com.nazarov.fluentplaywright.FluentPlaywright.$$;
import static com.nazarov.fluentplaywright.conditions.collection.CollectionConditions.size;
import static com.nazarov.fluentplaywright.conditions.element.Conditions.enabled;
import static com.nazarov.fluentplaywright.conditions.element.Conditions.visible;

import com.nazarov.fluentplaywright.element.PlaywrightElement;
import com.nazarov.fluentplaywright.element.PlaywrightElementCollection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CartPage extends BasePage {
  // Locators
  private final PlaywrightElement pageTitle = $(".title");
  private final PlaywrightElementCollection cartItems = $$(".cart_item");
  private final PlaywrightElement checkoutButton = $("#checkout");
  private final PlaywrightElement continueShoppingButton = $("#continue-shopping");

  @Override
  protected PlaywrightElement getPageIdentifier() {
    return pageTitle;
  }

  public CartPage verifyItemInCart(String itemName) {
    log.info("Verifying item in cart: {}", itemName);
    cartItems
        .filterByText(itemName)
        .shouldHave(size(1));
    return this;
  }

  public CartPage removeItem(String itemName) {
    log.info("Removing item from cart: {}", itemName);
    cartItems
        .filterByText(itemName)
        .first()
        .$("[data-test^='remove']")
        .click();
    return this;
  }

  public CartPage verifyCartItemsCount(int expectedCount) {
    log.info("Verifying cart items count: {}", expectedCount);
    cartItems.shouldHave(size(expectedCount));
    return this;
  }

  public InventoryPage continueShopping() {
    log.info("Continuing shopping");
    continueShoppingButton
        .shouldBe(visible(), enabled())
        .click();
    return new InventoryPage();
  }
}