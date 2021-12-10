package newapp.global.util;

import java.math.BigDecimal;

public class CastUtil {
  public static BigDecimal toBigDecimal(Object value) {
    if (value == null) return BigDecimal.ZERO;
    return BigDecimal.valueOf(Double.valueOf(String.valueOf(value)));
  }

  public static Long toLong(Object value) {
    if (value == null) return null;
    return Long.valueOf(String.valueOf(value));
  }
}
