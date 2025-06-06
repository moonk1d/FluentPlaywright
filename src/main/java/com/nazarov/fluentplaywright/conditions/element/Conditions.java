package com.nazarov.fluentplaywright.conditions.element;

import static com.nazarov.fluentplaywright.conditions.element.StateCondition.State.ATTACHED;
import static com.nazarov.fluentplaywright.conditions.element.StateCondition.State.DISABLED;
import static com.nazarov.fluentplaywright.conditions.element.StateCondition.State.EDITABLE;
import static com.nazarov.fluentplaywright.conditions.element.StateCondition.State.ENABLED;
import static com.nazarov.fluentplaywright.conditions.element.StateCondition.State.FOCUSED;
import static com.nazarov.fluentplaywright.conditions.element.StateCondition.State.HIDDEN;
import static com.nazarov.fluentplaywright.conditions.element.StateCondition.State.VISIBLE;

import com.nazarov.fluentplaywright.conditions.element.logical.And;
import com.nazarov.fluentplaywright.conditions.element.logical.Not;
import com.nazarov.fluentplaywright.conditions.element.logical.Or;
import java.time.Duration;

public class Conditions {

  private Conditions() {
  }

  public static Condition enabled() {
    return StateCondition.builder().state(ENABLED).build();
  }

  public static Condition disabled() {
    return StateCondition.builder().state(DISABLED).build();
  }

  public static Condition editable() {
    return StateCondition.builder().state(EDITABLE).build();
  }

  public static Condition focused() {
    return StateCondition.builder().state(FOCUSED).build();
  }

  public static Condition visible() {
    return StateCondition.builder().state(VISIBLE).build();
  }

  public static Condition hidden() {
    return StateCondition.builder().state(HIDDEN).build();
  }

  public static Condition attached() {
    return StateCondition.builder().state(ATTACHED).build();
  }

  public static Condition enabled(Duration timeout) {
    return StateCondition.builder()
        .state(ENABLED)
        .timeout(timeout)
        .build();
  }

  public static Condition disabled(Duration timeout) {
    return StateCondition.builder()
        .state(DISABLED)
        .timeout(timeout)
        .build();
  }

  public static Condition editable(Duration timeout) {
    return StateCondition.builder()
        .state(EDITABLE)
        .timeout(timeout)
        .build();
  }

  public static Condition focused(Duration timeout) {
    return StateCondition.builder()
        .state(FOCUSED)
        .timeout(timeout)
        .build();
  }

  public static Condition visible(Duration timeout) {
    return StateCondition.builder()
        .state(VISIBLE)
        .timeout(timeout)
        .build();
  }

  public static Condition hidden(Duration timeout) {
    return StateCondition.builder()
        .state(HIDDEN)
        .timeout(timeout)
        .build();
  }

  public static Condition attached(Duration timeout) {
    return StateCondition.builder()
        .state(ATTACHED)
        .timeout(timeout)
        .build();
  }

  public static Condition and(Condition... conditions) {
    return new And(conditions);
  }

  public static Condition or(Condition... conditions) {
    return new Or(conditions);
  }

  public static Condition not(Condition condition) {
    return new Not(condition);
  }

  public static Condition exactText(String expectedText) {
    return TextCondition.builder().expectedText(expectedText).build();
  }

  public static Condition exactText(String expectedText, Duration timeout) {
    return TextCondition.builder()
        .expectedText(expectedText)
        .timeout(timeout)
        .build();
  }

  public static Condition containsText(String expectedText) {
    return TextCondition.builder()
        .expectedText(expectedText)
        .useContains(true)
        .build();
  }

  public static Condition containsText(String expectedText, Duration timeout) {
    return TextCondition.builder()
        .expectedText(expectedText)
        .useContains(true)
        .timeout(timeout)
        .build();
  }

  public static Condition containsTextIgnoreCase(String expectedText) {
    return TextCondition.builder()
        .expectedText(expectedText)
        .ignoreCase(true)
        .useContains(true)
        .build();
  }

  public static Condition containsTextIgnoreCase(String expectedText, Duration timeout) {
    return TextCondition.builder()
        .expectedText(expectedText)
        .useContains(true)
        .ignoreCase(true)
        .timeout(timeout)
        .build();
  }

  public static Condition checked() {
    return CheckedCondition.builder().checked(true).build();
  }

  public static Condition checked(Duration timeout) {
    return CheckedCondition.builder()
        .checked(true)
        .timeout(timeout)
        .build();
  }

  public static Condition unchecked() {
    return CheckedCondition.builder().checked(false).build();
  }

  public static Condition unchecked(Duration timeout) {
    return CheckedCondition.builder()
        .checked(false)
        .timeout(timeout)
        .build();
  }

  public static Condition value(String expectedValue) {
    return ValueCondition.builder().expectedValue(expectedValue).build();
  }

  public static Condition value(String expectedValue, Duration timeout) {
    return ValueCondition.builder()
        .expectedValue(expectedValue)
        .timeout(timeout)
        .build();
  }

  public static Condition attribute(String name, String value) {
    return AttributeCondition.builder()
        .name(name)
        .value(value)
        .build();
  }

  public static Condition attribute(String name, String value, Duration timeout) {
    return AttributeCondition.builder()
        .name(name)
        .value(value)
        .timeout(timeout)
        .build();
  }

  public static Condition cssProperty(String property, String value) {
    return CssPropertyCondition.builder()
        .property(property)
        .value(value)
        .build();
  }

  public static Condition cssProperty(String property, String value, Duration timeout) {
    return CssPropertyCondition.builder()
        .property(property)
        .value(value)
        .timeout(timeout)
        .build();
  }

  public static Condition hasClass(String className) {
    return ClassCondition.builder()
        .className(className)
        .build();
  }

  public static Condition hasClass(String className, Duration timeout) {
    return ClassCondition.builder()
        .className(className)
        .timeout(timeout)
        .build();
  }

}