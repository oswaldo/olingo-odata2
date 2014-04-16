/*
 * Copyright 2013 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.olingo.odata2.annotation.processor.core.util;

import java.io.File;
import java.net.URL;
import java.util.List;

import junit.framework.Assert;

import org.apache.olingo.odata2.annotation.processor.core.util.ClassHelper.ClassValidator;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmKey;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.testutil.mock.AnnotatedEntity;
import org.junit.Test;

/**
 *
 */
public class ClassHelperTest {

  private final ClassValidator annotatedTestEntityInnerClasses = new ClassValidator() {
    @Override
    public boolean isClassValid(final Class<?> c) {
      return c.isAnnotationPresent(EdmEntityType.class)
          && c.getName().contains(ClassHelperTest.class.getSimpleName());
    }
  };

  private final ClassValidator annotatedEntityClasses = new ClassValidator() {
    @Override
    public boolean isClassValid(final Class<?> c) {
      return c.isAnnotationPresent(EdmEntityType.class);
    }
  };

  @Test
  public void loadSingleEntity() throws ODataException {
    String packageToScan = ClassHelperTest.class.getPackage().getName();

    //
    List<Class<?>> loadedClasses = ClassHelper.loadClasses(packageToScan, annotatedTestEntityInnerClasses);

    //
    Assert.assertEquals(1, loadedClasses.size());
    Assert.assertEquals(SimpleEntity.class.getName(), loadedClasses.get(0).getName());
  }

  @Test(expected = ClassFormatError.class)
  public void loadFromSpaceDir() throws Exception {
    URL currentPath = Thread.currentThread().getContextClassLoader().getResource(".");
    File folder = new File(currentPath.getFile(), "space space/package");
    folder.mkdirs();
    File classFile = new File(folder, "Invalid.class");
    classFile.createNewFile();
    String packageToScan = "space space.package";

    //
    List<Class<?>> loadedClasses = ClassHelper.loadClasses(packageToScan, annotatedTestEntityInnerClasses);

    //
    Assert.assertEquals(1, loadedClasses.size());
    Assert.assertEquals(SimpleEntity.class.getName(), loadedClasses.get(0).getName());
  }

  @Test
  public void loadSingleEntityFromJar() throws ODataException {
    String packageToScan = AnnotatedEntity.class.getPackage().getName();

    //
    List<Class<?>> loadedClasses = ClassHelper.loadClasses(packageToScan, annotatedEntityClasses);

    //
    Assert.assertEquals(1, loadedClasses.size());
    Assert.assertEquals(AnnotatedEntity.class.getName(), loadedClasses.get(0).getName());
  }

  //
  // The below classes are 'unused' within the code but must be declared for loading via
  // the 'ClassHelper'
  //

  @EdmEntityType
  @SuppressWarnings("unused")
  private class SimpleEntity {
    @EdmKey
    @EdmProperty
    Long id;
    @EdmProperty
    String name;
  }
}
