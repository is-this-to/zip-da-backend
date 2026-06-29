package com.zipdabackend.global.error.custom;

public class PropertyAccessDeniedException extends RuntimeException {
  public PropertyAccessDeniedException(String message) {
    super(message);
  }
}
