/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package org.apache.olingo.odata2.core.edm;

import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeException;

/**
 * Implementation of the EDM simple type SByte.
 * 
 */
public class EdmSByte extends AbstractSimpleType {

  private static final EdmSByte instance = new EdmSByte();

  public static EdmSByte getInstance() {
    return instance;
  }

  @Override
  public boolean isCompatible(final EdmSimpleType simpleType) {
    return simpleType instanceof Bit
        || simpleType instanceof Uint7
        || simpleType instanceof EdmSByte;
  }

  @Override
  public Class<?> getDefaultType() {
    return Byte.class;
  }

  @Override
  protected <T> T internalValueOfString(final String value, final EdmLiteralKind literalKind, final EdmFacets facets,
      final Class<T> returnType) throws EdmSimpleTypeException {
    Byte valueByte;
    try {
      valueByte = Byte.parseByte(value);
    } catch (final NumberFormatException e) {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_ILLEGAL_CONTENT.addContent(value), e);
    }

    if (returnType.isAssignableFrom(Byte.class)) {
      return returnType.cast(valueByte);
    } else if (returnType.isAssignableFrom(byte.class)) {
      return (T) valueByte;
    } else if (returnType.isAssignableFrom(Short.class)) {
      return returnType.cast(valueByte.shortValue());
    } else if (returnType.isAssignableFrom(short.class)) {
      return (T) Short.valueOf(valueByte.shortValue());
    } else if (returnType.isAssignableFrom(Integer.class)) {
      return returnType.cast(valueByte.intValue());
    } else if (returnType.isAssignableFrom(int.class)) {
      return (T) Integer.valueOf(valueByte.intValue());
    } else if (returnType.isAssignableFrom(Long.class)) {
      return returnType.cast(valueByte.longValue());
    } else if (returnType.isAssignableFrom(long.class)) {
      return (T) Long.valueOf(valueByte.longValue());
    } else if (CustomTypeConvertorRegistry.hasConvertorFor(returnType)) {
      return CustomTypeConvertorRegistry.convertToType(returnType, valueByte);
    } else {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_TYPE_NOT_SUPPORTED.addContent(returnType));
    }
  }

  @Override
  protected <T> String internalValueToString(final T value, final EdmLiteralKind literalKind, final EdmFacets facets)
      throws EdmSimpleTypeException {
    if (value instanceof Byte) {
      return value.toString();
    } else if (value instanceof Short || value instanceof Integer || value instanceof Long) {
      if (((Number) value).longValue() >= Byte.MIN_VALUE && ((Number) value).longValue() <= Byte.MAX_VALUE) {
        return value.toString();
      } else {
        throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_ILLEGAL_CONTENT.addContent(value));
      }
    } else {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_TYPE_NOT_SUPPORTED.addContent(value.getClass()));
    }
  }
}
