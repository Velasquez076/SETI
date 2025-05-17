package co.com.bancolombia.r2dbc.exceptions;

public class TechnicalException extends RuntimeException {

  public TechnicalException(String message) {
    super(message);
  }
}
