package org.apache.olingo.odata2.core.edm;

import java.lang.reflect.Member;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;

public class CustomTypeConvertor<T> {

  private final Class<T> type;
  private final EdmSimpleTypeKind edmSimpleTypeKind;

  public static <T> CustomTypeConvertor<T> newConvertor(Class<T> type, EdmSimpleTypeKind edmSimpleTypeKind) {
    return new CustomTypeConvertor<T>(type, edmSimpleTypeKind);
  }
  
  protected CustomTypeConvertor(Class<T> type, EdmSimpleTypeKind edmSimpleTypeKind) {
    super();
    this.type = type;
    this.edmSimpleTypeKind = edmSimpleTypeKind;
  }

  public Class<?> getType() {
    return type;
  }

  public String getTypeName() {
    return type.getName();
  }

  public EdmSimpleTypeKind getEdmSimpleType(Member member) {
    return edmSimpleTypeKind;
  }

}
