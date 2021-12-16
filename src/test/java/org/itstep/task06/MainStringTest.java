package org.itstep.task06;

import org.itstep.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.itstep.TestUtil.*;

class MainStringTest {

    public static final String CLASS_NAME = "org.itstep.task06.MainString";

    public static final Class<?> MAIN_STRING_CLAZZ;

    static {
        Class<?> cls;
        try {
            cls = Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException ex) {
            cls = null;
        }
        MAIN_STRING_CLAZZ = cls;
    }

    @Test
    @Order(1)
    @DisplayName("Проверка наличия класса MainString")
    void classExists() {
        Assertions.assertNotNull(MAIN_STRING_CLAZZ, "Класс " + CLASS_NAME + " не найден");
    }

    @Order(2)
    @ParameterizedTest(name = "{1} {0};")
    @DisplayName("Проверка закрытых полей")
    @CsvSource({"chars,char[]"})
    void privateFieldExists(String fieldName, Class<?> type) throws NoSuchFieldException {
        hasPrivateNoStaticField(MAIN_STRING_CLAZZ, fieldName, type);
    }

    @Order(2)
    @ParameterizedTest(name = "{0}()")
    @DisplayName("Публичный метод без аргументов")
    @ValueSource(strings = {"length", "clean"})
    void publicGetterExists(String getterName) throws NoSuchMethodException {
        hasPublicNoStaticMethod(MAIN_STRING_CLAZZ, getterName);
    }

    @Order(2)
    @ParameterizedTest(name = "{0}({1})")
    @DisplayName("Публичный метод с аргументами")
    @CsvSource({"concat,"+CLASS_NAME, "indexOf,int"})
    void publicGetterExists(String getterName, Class<?> argType) throws NoSuchMethodException {
        hasPublicNoStaticMethod(MAIN_STRING_CLAZZ, getterName, argType);
    }

    @Order(5)
    @Test
    @DisplayName("Проверка Конструкторов")
    void constructors() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] declaredConstructors = MAIN_STRING_CLAZZ.getDeclaredConstructors();
        Assertions.assertEquals(3, declaredConstructors.length, "Должно быть три конструктора");

        // Конструктор со строковым литералом
        Constructor<?> ctor = MAIN_STRING_CLAZZ.getDeclaredConstructor(CharSequence.class);

        Object str = TestUtil.getExpected(String.class);

        Object obj = ctor.newInstance(str);

        Method toString = MAIN_STRING_CLAZZ.getDeclaredMethod("toString");
        Assertions.assertEquals(str, toString.invoke(obj), "Конструктор с CharSequence");

        // Конструктор с символом и размером строки
        ctor = MAIN_STRING_CLAZZ.getDeclaredConstructor(char.class, int.class);

        Object ch = 'a';
        Object len = 10;

        obj = ctor.newInstance(ch, len);

        Assertions.assertEquals("aaaaaaaaaa", toString.invoke(obj), "Конструктор с символом и размером строки");
    }

    @Test
    @DisplayName("Проверка метода length()")
    void length() throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> ctor = MAIN_STRING_CLAZZ.getDeclaredConstructor(CharSequence.class);

        Object args = TestUtil.getExpected(String.class);
        Object obj = ctor.newInstance(args);

        Method length = MAIN_STRING_CLAZZ.getDeclaredMethod("length");
        Assertions.assertEquals(args.toString().length(), length.invoke(obj), "Метод length возвращает не верную длину строки");

        // Конструктор с символом и размером строки
        ctor = MAIN_STRING_CLAZZ.getDeclaredConstructor(char.class, int.class);

        Object ch = 'a';
        Object len = 10;

        obj = ctor.newInstance(ch, len);

        Assertions.assertEquals(len, length.invoke(obj), "Метод length возвращает не верную длину строки");

    }

    @Test
    @DisplayName("Проверка  метода clean()")
    void clean() throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> ctor = MAIN_STRING_CLAZZ.getDeclaredConstructor(CharSequence.class);

        Object args = TestUtil.getExpected(String.class);
        Object obj = ctor.newInstance(args);

        Method length = MAIN_STRING_CLAZZ.getDeclaredMethod("length");
        Assertions.assertEquals(args.toString().length(), length.invoke(obj), "Метод length возвращает не верную длину строки");

        Method clean = MAIN_STRING_CLAZZ.getDeclaredMethod("clean");
        clean.invoke(obj);

        Assertions.assertEquals(0, length.invoke(obj), "Метод length возвращает не верную длину строки после clean()");
    }

    @Test
    @DisplayName("Проверка метода concat()")
    void concat() throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> ctor = MAIN_STRING_CLAZZ.getDeclaredConstructor(CharSequence.class);

        Object args1 = TestUtil.getExpected(String.class);
        Object args2 = TestUtil.getExpected(String.class);
        Object obj1 = ctor.newInstance(args1);
        Object obj2 = ctor.newInstance(args2);

        Method concat = MAIN_STRING_CLAZZ.getDeclaredMethod("concat", MAIN_STRING_CLAZZ);

        Object obj3 = concat.invoke(obj1, obj2);

        Assertions.assertEquals(MAIN_STRING_CLAZZ, obj3.getClass(), "Метод concat должен возвращать объект типа "+ CLASS_NAME);
        Assertions.assertEquals(args1.toString() + args2.toString(), obj3.toString());

    }

    @Test
    @DisplayName("Проверка  метода indexOf()")
    void indexOf() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> ctor = MAIN_STRING_CLAZZ.getDeclaredConstructor(CharSequence.class);

        Object obj = ctor.newInstance("test string");
        Method indexOf = MAIN_STRING_CLAZZ.getDeclaredMethod("indexOf", int.class);
        Object i = indexOf.invoke(obj, 'i');
        Assertions.assertEquals(8, i);
    }
}