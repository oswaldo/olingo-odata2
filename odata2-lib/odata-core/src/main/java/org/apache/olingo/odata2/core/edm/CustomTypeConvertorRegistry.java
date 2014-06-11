package org.apache.olingo.odata2.core.edm;

import java.lang.reflect.Member;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;

public abstract class CustomTypeConvertorRegistry {

  private static final Map<String, CustomTypeConvertor> customConvertors =
      new HashMap<String, CustomTypeConvertor>();

  /**
   * Extension point that allows the addition of new supported types without leaking dependencies into the core library
   * code. For instance, one could use joda-time LocalDateTime instead of Calendar, so a CustomDateTimeTypeConvertor can
   * be registered for this specific objective.
   * 
   * @param customConvertor
   * @return A possibly replaced customConvertor previously registered for the same type as the new one
   */
  public static CustomTypeConvertor registerCustomConvertor(CustomTypeConvertor customConvertor) {
    return customConvertors.put(customConvertor.getTypeName(), customConvertor);
  }

  /**
   * @param jpaType
   * @return
   */
  public static boolean hasConvertorFor(Class<?> type) {
    return type != null && customConvertors.containsKey(type.getName());
  }

  /**
   * @param type
   * @param member
   * @return
   */
  public static EdmSimpleTypeKind convertToEdmSimpleType(Class<?> type, Member member) {
    return customConvertors.get(type.getName()).getEdmSimpleType(member);
  }

  /**
   * @param type
   * @param millis
   * @return
   */
  public static <T> T convertMillisToType(Class<T> type, long millis) {
    return ((CustomDateTimeTypeConvertor<T>) customConvertors.get(type.getName())).convertMillisToType(millis);
  }

  /**
   * @param type
   * @param calendar
   * @return
   */
  public static <T> T convertCalendarToType(Class<T> type, Calendar calendar) {
    return ((CustomDateTimeTypeConvertor<T>) customConvertors.get(type.getName())).convertCalendarToType(calendar);
  }

  /**
   * @param value
   * @return
   */
  public static Long convertValueToMillis(Object value) {
    return ((CustomDateTimeTypeConvertor) customConvertors.get(value.getClass().getName())).convertValueToMillis(value);
  }

}
