package com.nazarov.fluentplaywright.conditions.collection;

import com.nazarov.fluentplaywright.conditions.collection.logical.CollectionAnd;
import com.nazarov.fluentplaywright.conditions.collection.logical.CollectionNot;
import com.nazarov.fluentplaywright.conditions.collection.logical.CollectionOr;
import com.nazarov.fluentplaywright.conditions.element.StateCondition;
import java.time.Duration;
import java.util.List;

public class CollectionConditions {

  private CollectionConditions() {
  }

  // Size conditions
  public static CollectionCondition size(int expectedSize) {
    return SizeCondition.builder()
        .expectedSize(expectedSize)
        .build();
  }

  public static CollectionCondition size(int expectedSize, Duration timeout) {
    return SizeCondition.builder()
        .expectedSize(expectedSize)
        .timeout(timeout)
        .build();
  }

  public static CollectionCondition sizeGreaterThan(int size) {
    return SizeCondition.builder()
        .expectedSize(size)
        .comparison(SizeCondition.Comparison.GREATER_THAN)
        .build();
  }

  public static CollectionCondition sizeLessThan(int size) {
    return SizeCondition.builder()
        .expectedSize(size)
        .comparison(SizeCondition.Comparison.LESS_THAN)
        .build();
  }

  public static CollectionCondition sizeBetween(int min, int max) {
    return SizeCondition.builder()
        .minSize(min)
        .maxSize(max)
        .comparison(SizeCondition.Comparison.BETWEEN)
        .build();
  }

  public static CollectionCondition empty() {
    return size(0);
  }

  public static CollectionCondition notEmpty() {
    return sizeGreaterThan(0);
  }

  // Text conditions
  public static CollectionCondition texts(List<String> expectedTexts) {
    return CollectionTextCondition.builder()
        .expectedTexts(expectedTexts)
        .build();
  }

  public static CollectionCondition texts(List<String> expectedTexts, Duration timeout) {
    return CollectionTextCondition.builder()
        .expectedTexts(expectedTexts)
        .timeout(timeout)
        .build();
  }

  public static CollectionCondition containsTexts(List<String> texts) {
    return CollectionTextCondition.builder()
        .expectedTexts(texts)
        .useContains(true)
        .build();
  }

  public static CollectionCondition containsTextsIgnoreCase(List<String> texts) {
    return CollectionTextCondition.builder()
        .expectedTexts(texts)
        .useContains(true)
        .ignoreCase(true)
        .build();
  }

  // Attribute conditions
  public static CollectionCondition allHaveAttribute(String name, String value) {
    return CollectionAttributeCondition.builder()
        .name(name)
        .value(value)
        .build();
  }

  public static CollectionCondition allHaveAttribute(String name, String value, Duration timeout) {
    return CollectionAttributeCondition.builder()
        .name(name)
        .value(value)
        .timeout(timeout)
        .build();
  }

  // Class conditions
  public static CollectionCondition allHaveClass(String className) {
    return CollectionClassCondition.builder()
        .className(className)
        .build();
  }

  public static CollectionCondition allHaveClass(String className, Duration timeout) {
    return CollectionClassCondition.builder()
        .className(className)
        .timeout(timeout)
        .build();
  }

  // State conditions
  public static CollectionCondition allVisible() {
    return CollectionStateCondition.builder()
        .state(StateCondition.State.VISIBLE)
        .build();
  }

  public static CollectionCondition allEnabled() {
    return CollectionStateCondition.builder()
        .state(StateCondition.State.ENABLED)
        .build();
  }

  // Logical conditions
  public static CollectionCondition and(CollectionCondition... conditions) {
    return new CollectionAnd(conditions);
  }

  public static CollectionCondition or(CollectionCondition... conditions) {
    return new CollectionOr(conditions);
  }

  public static CollectionCondition not(CollectionCondition condition) {
    return new CollectionNot(condition);
  }
}