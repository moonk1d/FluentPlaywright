package com.nazarov.fluentplaywright.example.pom;

import static com.nazarov.fluentplaywright.FluentPlaywright.$;
import static com.nazarov.fluentplaywright.FluentPlaywright.$$;
import static com.nazarov.fluentplaywright.conditions.collection.CollectionConditions.size;
import static com.nazarov.fluentplaywright.conditions.element.Conditions.exactText;
import static com.nazarov.fluentplaywright.conditions.element.Conditions.hidden;
import static com.nazarov.fluentplaywright.conditions.element.Conditions.visible;

import com.nazarov.fluentplaywright.element.PlaywrightElement;
import com.nazarov.fluentplaywright.element.PlaywrightElementCollection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryPage extends BasePage {
  // Locators
  private final PlaywrightElement pageTitle = $(".title");
  private final PlaywrightElementCollection inventoryItems = $$(".inventory_item");
  private final PlaywrightElement cartBadge = $(".shopping_cart_badge");
  private final PlaywrightElement cartLink = $(".shopping_cart_link");
  private final PlaywrightElement sortDropdown = $("[data-test='product_sort_container']");

  @Override
  protected PlaywrightElement getPageIdentifier() {
    return pageTitle;
  }

  public InventoryPage verifyPageTitle(String expectedTitle) {
    log.info("Verifying page title: {}", expectedTitle);
    pageTitle
        .shouldBe(visible())
        .shouldHave(exactText(expectedTitle));
    return this;
  }

  public InventoryPage addItemToCart(String itemName) {
    log.info("Adding item to cart: {}", itemName);
    inventoryItems
        .filterByText(itemName)
        .first()
        .$("[data-test^='add-to-cart']")
        .click();
    return this;
  }

  public InventoryPage removeItemFromCart(String itemName) {
    log.info("Removing item from cart: {}", itemName);
    inventoryItems
        .filterByText(itemName)
        .first()
        .$("[data-test^='remove']")
        .click();
    return this;
  }

  public InventoryPage verifyItemCount(int expectedCount) {
    log.info("Verifying item count: {}", expectedCount);
    if (expectedCount == 0) {
      cartBadge.shouldBe(hidden());
    } else {
      cartBadge
          .shouldBe(visible())
          .shouldHave(exactText(String.valueOf(expectedCount)));
    }
    return this;
  }

  public CartPage openCart() {
    log.info("Opening cart");
    cartLink
        .shouldBe(visible())
        .click();
    return new CartPage();
  }

  public InventoryPage sortBy(String sortOption) {
    log.info("Sorting by: {}", sortOption);
    sortDropdown
        .shouldBe(visible())
        .click();
    $("option[value='" + sortOption + "']").click();
    return this;
  }

  public InventoryPage verifyItemsCount(int expectedCount) {
    log.info("Verifying items count: {}", expectedCount);
    inventoryItems.shouldHave(size(expectedCount));
    return this;
  }
}