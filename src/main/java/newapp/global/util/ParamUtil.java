package newapp.global.util;

import java.util.Map;

public class ParamUtil {
  /**
   * param 은 HashMap<String, Object> Type
   * @param param
   * @param key
   * @return
   */
  public static String getParam(Map<String, Object> param, String key) {
    if (param == null || key == null) return null;
    return String.valueOf(param.get(key));
  }
}
