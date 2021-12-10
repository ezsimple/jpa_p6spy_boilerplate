package newapp.global.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalUtil {

  /**
   * 3자리 콤마
   * 원화와 엔화는 정수로만(소수점이하는 절삭), 달러는 소수점 두자리수까지만(그 이하는 절삭)
   * @param Lang
   * @param value
   * @return
   */
  public static String getComma(String Lang, BigDecimal value) {
    String pattern = ("EN".equals(Lang))?"###,###.##":"###,###";
    DecimalFormat decimalFormat = new DecimalFormat(pattern);
    return decimalFormat.format(value);
  }

}
