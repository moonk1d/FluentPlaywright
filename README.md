# FluentPlaywright

A fluent, user-friendly wrapper for Playwright Java that provides a Selenide-like experience. This library simplifies test automation by providing an intuitive API, built-in waiting mechanisms, and powerful assertions.

## Features

- Fluent, chainable API
- Built-in smart waiting mechanisms
- Thread-safe parallel execution support
- Rich collection of conditions and assertions
- Easy-to-use element and collection handling
- Multi-page support
- Comprehensive logging
- Type-safe configuration

## Comparison with Pure Playwright

### Basic Element Interaction

```java
// Pure Playwright
page.locator(".submit-button").waitFor();
page.locator(".submit-button").click();
expect(page.locator(".success-message")).toBeVisible();

// FluentPlaywright
$(".submit-button")
    .shouldBe(visible())
    .click();
$(".success-message").shouldBe(visible());
```

### Form Filling

```java
// Pure Playwright
page.locator("#username").fill("user");
page.locator("#password").fill("pass");
page.locator("button[type='submit']").click();
expect(page.locator(".welcome")).toHaveText("Welcome, user!");

// FluentPlaywright
$("#username").setValue("user");
$("#password").setValue("pass");
$("button[type='submit']").click();
$(".welcome").shouldHave(exactText("Welcome, user!"));
```

### Working with Collections

```java
// Pure Playwright
Locator items = page.locator(".items");
expect(items).toHaveCount(5);
for (int i = 0; i < 5; i++) {
    expect(items.nth(i)).toBeVisible();
}

// FluentPlaywright
$$(".items")
    .shouldHave(size(5))
    .shouldHave(allVisible());
```

## Getting Started

### Maven Dependency

```xml
<dependency>
    <groupId>com.nazarov</groupId>
    <artifactId>FluentPlaywright</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### Basic Configuration

Create `playwright.properties` in your resources folder:

```properties
# Browser configuration
browser.type=CHROMIUM
headless=false
slow.mo=0

# Timeouts (in milliseconds)
locatorTimeout=4000
assertionTimeout=4000
waitForTimeout=4000

# Base URL
base.url=https://example.com

# Tracing configuration
tracing=false
tracePath=traces/trace.zip

# Video recording
recordVideo=false
videoPath=videos/
```

### Test Setup

Basic test setup with JUnit 5:

```java
public class TestFixture {

  private static PlaywrightConfig config;

  @BeforeAll
  static void setUpConfig() {
    config = ConfigurationReader.readConfiguration();
  }

  @BeforeEach
  void setUp() {
    PlaywrightManager.initialize(config);
  }

  @AfterEach
  void tearDown() {
    PlaywrightManager.closeSession();
  }

}

class LoginTest extends TestFixture {
    @Test
    void successfulLogin() {
        $(".login-form")
            .shouldBe(visible())
            .$("input[name='username']").setValue("testuser");
        $("input[name='password']").setValue("password");
        $("button[type='submit']").click();
        
        $(".welcome-message")
            .shouldBe(visible())
            .shouldHave(exactText("Welcome, testuser!"));
    }
}
```

## Key Features

### Element Interactions

```java
// Basic interactions
$(".element")
    .shouldBe(visible())
    .click();

// Form interactions
$("#username")
    .setValue("user")
    .pressKey("Enter");

// Complex conditions
$(".dynamic-element")
    .shouldBe(and(
        visible(),
        enabled(),
        attribute("data-ready", "true")
    ));
```

### Collection Operations

```java
// Size assertions
$$(".items").shouldHave(size(5));

// Text assertions
$$(".items").shouldHave(texts(List.of("Item 1", "Item 2", "Item 3")));

// Filtering
$$(".items")
    .filterByText("Special")
    .shouldHave(size(2));

// Complex assertions
$$(".dynamic-items")
    .shouldHave(and(
        size(3),
        allVisible(),
        allHaveAttribute("data-loaded", "true")
    ));
```

### Multi-page Handling

```java
// Working with multiple pages
Page popup = switchToPopup(() -> 
    $(".open-popup-button").click()
);

// Switch between pages
switchToPage(popup);
$(".popup-content").shouldBe(visible());

switchToPageByUrl(".*checkout.*");
$(".checkout-form").shouldBe(visible());
```

### Custom Configurations

```java
PlaywrightConfig config = PlaywrightConfig.builder()
    .browserType(BrowserType.CHROMIUM)
    .headless(false)
    .locatorTimeout(Duration.ofSeconds(10))
    .assertionTimeout(Duration.ofSeconds(5))
    .waitForTimeout(Duration.ofSeconds(15))
    .tracing(true)
    .tracePath("traces/my-test.zip")
    .build();

PlaywrightManager.initialize(config);
```

## Available Conditions

### Element Conditions
- State: `visible()`, `hidden()`, `enabled()`, `disabled()`, `editable()`, `focused()`
- Text: `exactText()`, `containsText()`, `containsTextIgnoreCase()`
- Attributes: `attribute()`, `hasClass()`, `cssProperty()`
- Form: `checked()`, `unchecked()`, `value()`
- Logical: `and()`, `or()`, `not()`

### Collection Conditions
- Size: `size()`, `sizeGreaterThan()`, `sizeLessThan()`, `empty()`
- Content: `texts()`, `containsTexts()`, `containsTextsIgnoreCase()`
- State: `allVisible()`, `allEnabled()`
- Attributes: `allHaveAttribute()`, `allHaveClass()`

## Best Practices

1. Use Page Objects:

```java
public class LoginPage {
    public LoginPage enterCredentials(String username, String password) {
        $("#username").setValue(username);
        $("#password").setValue(password);
        return this;
    }

    public HomePage clickLogin() {
        $(".login-button").click();
        return new HomePage();
    }
}
```

2. Use Custom Conditions:

```java
public static Condition customCondition(String description, Predicate<PlaywrightElement> predicate) {
    return element -> {
        if (!predicate.test(element)) {
            throw new ConditionNotMetException("Custom condition failed: " + description);
        }
    };
}
```

3. Handle Dynamic Content:

```java
$$(".dynamic-items")
    .waitFor()
    .filterByAttribute("data-loaded", "true")
    .shouldHave(size(5));
```

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.