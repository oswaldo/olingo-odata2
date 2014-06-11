package org.apache.olingo.odata2.core.edm;

import java.util.Calendar;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;

public abstract class CustomDateTimeTypeConvertor<T> extends CustomTypeConvertor<T> {

  public CustomDateTimeTypeConvertor(Class<T> type, EdmSimpleTypeKind edmSimpleTypeKind) {
    super(type, edmSimpleTypeKind);
  }

  public abstract T convertMillisToType(long millis);

  public abstract Long convertValueToMillis(T value);

  public T convertCalendarToType(Calendar calendar) {
    return convertMillisToType(calendar.getTimeInMillis());
  }

}
