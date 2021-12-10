package newapp.global.util;

import org.apache.commons.lang3.StringUtils;

public class JpaUtil {
  public static String toYN(String value) {
    return "Y".equals(StringUtils.upperCase(value)) ? "Y" : "N";
  }
}
