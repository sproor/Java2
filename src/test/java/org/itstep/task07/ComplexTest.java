package org.itstep.task07;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.itstep.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ComplexTest {

    public static final String CLASS_NAME = "org.itstep.task07.Complex";

    public static final Class<?> CLASS;

    static {
        Class<?> cls;
        try {
            cls = Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException ex) {
            cls = null;
        }
        CLASS = cls;
    }

    @Test
    @DisplayName("Проверка наличия класса Complex")
    void classExists() {
        Assertions.assertNotNull(CLASS, "Класс " + CLASS_NAME + " не найден");
    }

    @ParameterizedTest(name = "{1} {0};")
    @DisplayName("Проверка закрытых полей")
    @CsvSource({"real,double", "imaginary,double"})
    void privateFieldExists(String fieldName, Class<?> type) throws NoSuchFieldException {
        hasPrivateNoStaticField(CLASS, fieldName, type);
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка геттеров")
    @ValueSource(strings = {"getReal", "getImaginary"})
    void publicGetterExists(String getterName) throws NoSuchMethodException {
        hasPublicNoStaticMethod(CLASS, getterName);
    }

    @ParameterizedTest(name = "{1}")
    @DisplayName("Проверка сеттеров")
    @CsvSource({"double,setReal", "double,setImaginary"})
    void publicSetterExists(Class<?> clazz, String setterName) throws NoSuchMethodException {
        hasPublicNoStaticMethod(CLASS, setterName, clazz);
    }

    @ParameterizedTest(name = "{1}")
    @DisplayName("Проверка сеттеров/геттеров")
    @CsvSource({"double,real", "double,imaginary"})
    void setterGetterWork(Class<?> clazz, String fieldName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        getAndSetCheck(CLASS, clazz, fieldName);
    }

    @Test
    @DisplayName("Проверка Конструкторов")
    void constructors() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] declaredConstructors = CLASS.getDeclaredConstructors();
        Assertions.assertEquals(2, declaredConstructors.length, "Должно быть два конструктора");

        // Конструктор со двумя аргументами
        Constructor<?> ctor = CLASS.getDeclaredConstructor(double.class, double.class);

        Object obj = ctor.newInstance(10.0, 10.0);

        Method toString = CLASS.getDeclaredMethod("toString");
        Assertions.assertEquals("10.0 + 10.0i", toString.invoke(obj), "Конструктор параметрами");
    }


    @Test
    @DisplayName("Проверка метода toString()")
    public void testToString() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // Arrange, Act and Assert
        Constructor<?> ctor = CLASS.getDeclaredConstructor(double.class, double.class);

        assertEquals("10.0 + 10.0i", (ctor.newInstance(10.0, 10.0)).toString());
        assertEquals("10.0i", (ctor.newInstance(0.0, 10.0)).toString());
        assertEquals("10.0", (ctor.newInstance(10.0, 0.0)).toString());
        assertEquals("10.0 - 0.5i", (ctor.newInstance(10.0, -0.5)).toString());
    }

    @Test
    @DisplayName("Проверка метода plus()")
    public void testPlus() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // Arrange
        Constructor<?> ctor = CLASS.getDeclaredConstructor(double.class, double.class);

        Object one = ctor.newInstance(10.0, 10.0);
        Object two = ctor.newInstance(10.0, 10.0);

        Method methodPlus = CLASS.getDeclaredMethod("plus", CLASS);

        // Act and Assert
        assertEquals("20.0 + 20.0i", methodPlus.invoke(one, two).toString());
    }

    @Test
    @DisplayName("Проверка метода minus()")
    public void testMinus() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // Arrange
        Constructor<?> ctor = CLASS.getDeclaredConstructor(double.class, double.class);

        Object one = ctor.newInstance(10.0, 10.0);
        Object two = ctor.newInstance(10.0, 10.0);

        Method methodMinus = CLASS.getDeclaredMethod("minus", CLASS);

        // Act and Assert
        assertEquals("0.0", methodMinus.invoke(one, two).toString());
    }

    @Test
    @DisplayName("Проверка метода times() - 1")
    public void testTimes() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // Arrange
        Constructor<?> ctor = CLASS.getDeclaredConstructor(double.class, double.class);

        Object one = ctor.newInstance(10.0, 10.0);
        Object two = ctor.newInstance(10.0, 10.0);

        Method methodTimes = CLASS.getDeclaredMethod("times", CLASS);

        // Act and Assert
        assertEquals("200.0i", methodTimes.invoke(one, two).toString());
    }

    @Test
    @DisplayName("Проверка метода times() - 2")
    public void testTimes2() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        // Arrange
        Constructor<?> ctor = CLASS.getDeclaredConstructor(double.class, double.class);

        Object one = ctor.newInstance(10.0, 10.0);
        Object two = ctor.newInstance(1.0, 1.0);

        Method methodTimes = CLASS.getDeclaredMethod("times", CLASS);

        // Act and Assert
        assertEquals("20.0i", methodTimes.invoke(one, two).toString());
    }

    @Test
    @DisplayName("Проверка метода equals()")
    public void testEquals() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // Arrange, Act and Assert
        Constructor<?> ctor = CLASS.getDeclaredConstructor(double.class, double.class);

        Object one = ctor.newInstance(10.0, 10.0);
        assertNotEquals(one, "x");
        assertNotEquals(one, null);
    }
}

