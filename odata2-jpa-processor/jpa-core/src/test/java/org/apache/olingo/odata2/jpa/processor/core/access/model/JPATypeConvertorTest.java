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
package org.apache.olingo.odata2.jpa.processor.core.access.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.EnumType;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.junit.Test;

public class JPATypeConvertorTest {

  private EdmSimpleTypeKind edmSimpleKindTypeString;
  private EdmSimpleTypeKind edmSimpleKindTypeByteArr;
  private EdmSimpleTypeKind edmSimpleKindTypeLong;
  private EdmSimpleTypeKind edmSimpleKindTypeShort;
  private EdmSimpleTypeKind edmSimpleKindTypeInteger;
  private EdmSimpleTypeKind edmSimpleKindTypeDouble;
  private EdmSimpleTypeKind edmSimpleKindTypeFloat;
  private EdmSimpleTypeKind edmSimpleKindTypeBigDecimal;
  private EdmSimpleTypeKind edmSimpleKindTypeByte;
  private EdmSimpleTypeKind edmSimpleKindTypeBoolean;
  private EdmSimpleTypeKind edmSimpleKindTypeUUID;
  private EdmSimpleTypeKind edmSimpleKindTypeEnumAsString;

  @Test
  public void testConvertToEdmSimpleType() {
    String str = "entity";
    byte[] byteArr = new byte[3];
    Long longObj = new Long(0);
    Short shortObj = new Short((short) 0);
    Integer integerObj = new Integer(0);
    Double doubleObj = new Double(0);
    Float floatObj = new Float(0);
    BigDecimal bigDecimalObj = new BigDecimal(0);
    Byte byteObj = new Byte((byte) 0);
    Boolean booleanObj = Boolean.TRUE;
    UUID uUID = new UUID(0, 0);
    EnumType someEnum = EnumType.ORDINAL;

    try {
      edmSimpleKindTypeString = JPATypeConvertor.convertToEdmSimpleType(str.getClass(), null);
      edmSimpleKindTypeByteArr = JPATypeConvertor.convertToEdmSimpleType(byteArr.getClass(), null);
      edmSimpleKindTypeLong = JPATypeConvertor.convertToEdmSimpleType(longObj.getClass(), null);
      edmSimpleKindTypeShort = JPATypeConvertor.convertToEdmSimpleType(shortObj.getClass(), null);
      edmSimpleKindTypeInteger = JPATypeConvertor.convertToEdmSimpleType(integerObj.getClass(), null);
      edmSimpleKindTypeDouble = JPATypeConvertor.convertToEdmSimpleType(doubleObj.getClass(), null);
      edmSimpleKindTypeFloat = JPATypeConvertor.convertToEdmSimpleType(floatObj.getClass(), null);
      edmSimpleKindTypeBigDecimal = JPATypeConvertor.convertToEdmSimpleType(bigDecimalObj.getClass(), null);
      edmSimpleKindTypeByte = JPATypeConvertor.convertToEdmSimpleType(byteObj.getClass(), null);
      edmSimpleKindTypeBoolean = JPATypeConvertor.convertToEdmSimpleType(booleanObj.getClass(), null);
      edmSimpleKindTypeEnumAsString = JPATypeConvertor.convertToEdmSimpleType(someEnum.getClass(), null);
      /*
       * edmSimpleKindTypeDate = JPATypeConvertor
       * .convertToEdmSimpleType(dateObj.getClass(),null);
       */
      edmSimpleKindTypeUUID = JPATypeConvertor.convertToEdmSimpleType(uUID.getClass(), null);
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

    assertEquals(EdmSimpleTypeKind.String, edmSimpleKindTypeString);
    assertEquals(EdmSimpleTypeKind.Binary, edmSimpleKindTypeByteArr);
    assertEquals(EdmSimpleTypeKind.Int64, edmSimpleKindTypeLong);
    assertEquals(EdmSimpleTypeKind.Int16, edmSimpleKindTypeShort);
    assertEquals(EdmSimpleTypeKind.Int32, edmSimpleKindTypeInteger);
    assertEquals(EdmSimpleTypeKind.Double, edmSimpleKindTypeDouble);
    assertEquals(EdmSimpleTypeKind.Single, edmSimpleKindTypeFloat);
    assertEquals(EdmSimpleTypeKind.Decimal, edmSimpleKindTypeBigDecimal);
    assertEquals(EdmSimpleTypeKind.Byte, edmSimpleKindTypeByte);
    assertEquals(EdmSimpleTypeKind.Boolean, edmSimpleKindTypeBoolean);
    // assertEquals(EdmSimpleTypeKind.DateTime, edmSimpleKindTypeDate);
    assertEquals(EdmSimpleTypeKind.Guid, edmSimpleKindTypeUUID);
    assertEquals(EdmSimpleTypeKind.String, edmSimpleKindTypeEnumAsString);
  }

}
