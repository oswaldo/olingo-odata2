package org.apache.olingo.odata2.core.edm;

import java.lang.reflect.Member;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;

public abstract class CustomTypeConvertorRegistry {

  private static final Map<String, CustomTypeConvertor> customConvertors =
      new HashMap<String, CustomTypeConvertor>();

  /**
   * Extension point that allows the addition of new supported types without leaking dependencies into the core library
   * code. For instance, one could use joda-time LocalDateTime instead of Calendar, so a CustomDateTimeTypeConvertor can
   * be registered for this specific objective.
   * 
   * @param convertors
   * @return A possibly replaced customConvertors previously registered for the same types as the new ones
   */
  public static Set<CustomTypeConvertor> register(CustomTypeConvertor... convertors) {
    Set<CustomTypeConvertor> replaced = new HashSet<CustomTypeConvertor>();
    for (CustomTypeConvertor customConvertor : convertors) {
      CustomTypeConvertor previous = customConvertors.put(customConvertor.getTypeName(), customConvertor);
      if (previous != null) {
        replaced.add(previous);
      }
    }
    return replaced;
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
    String className = type.getName();
    if (!customConvertors.containsKey(className)) {
      throw new IllegalStateException(String.format("Unknown type %s", className));
    }
    return customConvertors.get(className).getEdmSimpleType(member);
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

  // Utility methods for simpler registration of simple types

  private static Set<CustomTypeConvertor> registerSimple(EdmSimpleTypeKind kind, Class<?>... types) {
    Set<CustomTypeConvertor> replaced = new HashSet<CustomTypeConvertor>();
    for (Class<?> type : types) {
      replaced.addAll(register(CustomTypeConvertor.newConvertor(type, kind)));
    }
    return replaced;
  }

  public static Set<CustomTypeConvertor> registerBinary(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.Binary, types);
  }

  public static Set<CustomTypeConvertor> registerBoolean(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.Boolean, types);
  }

  public static Set<CustomTypeConvertor> registerByte(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.Byte, types);
  }

  public static Set<CustomTypeConvertor> registerDecimal(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.Decimal, types);
  }

  public static Set<CustomTypeConvertor> registerDouble(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.Double, types);
  }

  public static Set<CustomTypeConvertor> registerGuid(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.Guid, types);
  }

  public static Set<CustomTypeConvertor> registerInt16(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.Int16, types);
  }

  public static Set<CustomTypeConvertor> registerInt32(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.Int32, types);
  }

  public static Set<CustomTypeConvertor> registerInt64(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.Int64, types);
  }

  public static Set<CustomTypeConvertor> registerSByte(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.SByte, types);
  }

  public static Set<CustomTypeConvertor> registerSingle(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.Single, types);
  }

  public static Set<CustomTypeConvertor> registerString(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.String, types);
  }

  public static Set<CustomTypeConvertor> registerNull(Class<?>... types) {
    return registerSimple(EdmSimpleTypeKind.Null, types);
  }

}
