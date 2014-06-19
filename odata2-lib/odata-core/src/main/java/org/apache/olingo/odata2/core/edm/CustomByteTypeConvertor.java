package org.apache.olingo.odata2.core.edm;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;

public abstract class CustomByteTypeConvertor<T> extends CustomTypeConvertor<T> {

  public CustomByteTypeConvertor(Class<T> type, EdmSimpleTypeKind edmSimpleTypeKind) {
    super(type, edmSimpleTypeKind);
  }

  public abstract T convertToType(Byte valueByte);

}
