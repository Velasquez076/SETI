package co.com.bancolombia.api.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {

  OK(200, "OK"),
  CREATE(201, "CREATE"),
  BAD_REQUEST(400, "BAD_REQUEST"),
  UNKNOW_ERROR(666, "UNEXPECTED ERROR");

  private final int code;
  private final String message;
}
