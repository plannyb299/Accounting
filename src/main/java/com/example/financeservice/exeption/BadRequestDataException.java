package com.example.financeservice.exeption;

public class BadRequestDataException extends RuntimeException{

  public BadRequestDataException(String message) {
    super(message);
  }

  public BadRequestDataException(String message, Throwable cause) {
    super(message, cause);
  }

  public BadRequestDataException(final Throwable cause) {
    super(cause);
  }
}
