package org.itstep.task05;

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
import static org.junit.jupiter.api.Assertions.fail;

class MoneyTest {

    public static final String CLASS_NAME = "org.itstep.task05.Money";

    public static final Class<?> MONEY_CLAZZ;

    static {
        Class<?> cls;
        try {
            cls = Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException ex) {
            cls = null;
        }
        MONEY_CLAZZ = cls;
    }

    @Test
    @Order(1)
    @DisplayName("Проверка наличия класса Money")
    void classExists() {
        Assertions.assertNotNull(MONEY_CLAZZ, "Класс " + CLASS_NAME + " не найден");
    }

    @Order(2)
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка закрытых полей")
    @ValueSource(strings = {"hryvnia", "kopecks"})
    void privateFieldExists(String fieldName) throws NoSuchFieldException {
        hasPrivateNoStaticField(MONEY_CLAZZ, fieldName);
    }

    @Order(2)
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка геттеров")
    @ValueSource(strings = {"getHryvnia", "getKopecks"})
    void publicGetterExists(String getterName) throws NoSuchMethodException {
        hasPublicNoStaticMethod(MONEY_CLAZZ, getterName);
    }

    @Order(3)
    @ParameterizedTest(name = "{1}")
    @DisplayName("Проверка сеттеров")
    @CsvSource({"long,setHryvnia", "byte,setKopecks"})
    void publicSetterExists(Class<?> clazz, String setterName) throws NoSuchMethodException {
        hasPublicNoStaticMethod(MONEY_CLAZZ, setterName, clazz);
    }

    @Order(4)
    @ParameterizedTest(name = "{1}")
    @DisplayName("Проверка сеттеров/геттеров")
    @CsvSource({"long,hryvnia", "byte,kopecks"})
    void setterGetterWork(Class<?> clazz, String fieldName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        getAndSetCheck(MONEY_CLAZZ, clazz, fieldName);
    }

    @Order(5)
    @Test
    @DisplayName("Проверка Конструкторов")
    void constructors() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] declaredConstructors = MONEY_CLAZZ.getDeclaredConstructors();
        Assertions.assertEquals(3, declaredConstructors.length);

        Constructor<?> ctor = MONEY_CLAZZ.getDeclaredConstructor(long.class, byte.class);

        Object numerator = TestUtil.getExpected(long.class);
        Object denominator = TestUtil.getExpected(byte.class);

        Object obj = ctor.newInstance(numerator, denominator);

        Method getNumerator = MONEY_CLAZZ.getDeclaredMethod("getHryvnia");
        Assertions.assertEquals(numerator, getNumerator.invoke(obj));

        Method getDenominator = MONEY_CLAZZ.getDeclaredMethod("getKopecks");
        Assertions.assertEquals(denominator, getDenominator.invoke(obj));
    }

    Object getMoney(Object hryvnia, Object kopeecks) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> ctor = MONEY_CLAZZ.getDeclaredConstructor(long.class, byte.class);
        return ctor.newInstance(hryvnia, kopeecks);
    }

    void mathCheck(Object[] args, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Object first = getMoney(args[0], args[1]);
        Object second = getMoney(args[2], args[3]);

        Method declaredMethod = MONEY_CLAZZ.getDeclaredMethod(methodName, MONEY_CLAZZ);
        Object result = declaredMethod.invoke(first, second);
        Assertions.assertEquals(MONEY_CLAZZ, result.getClass(), "Метод "+methodName+" должен возвращать Money");

        Method getHryvnia = MONEY_CLAZZ.getDeclaredMethod("getHryvnia");
        Method getKopecks = MONEY_CLAZZ.getDeclaredMethod("getKopecks");

        Assertions.assertEquals(args[4], getHryvnia.invoke(result), "Неверное количество гривен");
        Assertions.assertEquals(args[5], getKopecks.invoke(result), "Неверное количество копеек");
    }

    void mathCheckDouble(Object[] args, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Object first = getMoney(args[0], args[1]);

        Method declaredMethod = MONEY_CLAZZ.getDeclaredMethod(methodName, double.class);
        Object result = declaredMethod.invoke(first, args[2]);
        Assertions.assertEquals(MONEY_CLAZZ, result.getClass(), "Метод "+methodName+" должен возвращать Money");

        Method getHryvnia = MONEY_CLAZZ.getDeclaredMethod("getHryvnia");
        Method getKopecks = MONEY_CLAZZ.getDeclaredMethod("getKopecks");

        Assertions.assertEquals(args[3], getHryvnia.invoke(result), "Неверное количество гривен");
        Assertions.assertEquals(args[4], getKopecks.invoke(result), "Неверное количество копеек");
    }

    @Order(6)
    @DisplayName("Проверка метода сложения")
    @ParameterizedTest(name = "{0},{1}+{2},{3}={4},{5}")
    @CsvSource({"1,3,1,3,2,6","2,5,2,5,4,10", "1,2,1,2,2,4"})
    void addition(long h1, byte k1, long h2, byte k2, long rh, byte rk)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        mathCheck(new Object[]{h1, k1, h2, k2, rh, rk}, "addition");
    }

    @Order(7)
    @DisplayName("Проверка метода вычитания")
    @ParameterizedTest(name = "{0},{1}-{2},{3}={4},{5}")
    @CsvSource({"3,10,1,3,2,7","3,5,2,5,1,0", "5,5,1,4,4,1"})
    void subtraction(long num1, byte den1, long num2, byte den2, long rnum, byte rden) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        mathCheck(new Object[]{num1, den1, num2, den2, rnum, rden}, "subtraction");
    }

    @Order(8)
    @DisplayName("Проверка метода умножения")
    @ParameterizedTest(name = "{0},{1}*{2}={3},{4}")
    @CsvSource({"5,0,2,10,0","3,5,2,6,10", "10,0,10,100,0"})
    void multiplication(long num1, byte den1, double value, long rnum, byte rden) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        mathCheckDouble(new Object[]{num1, den1, value, rnum, rden}, "multiply");
    }

    @Order(9)
    @DisplayName("Проверка метода деления")
    @ParameterizedTest(name = "{0},{1}/{2}={3},{4}")
    @CsvSource({"2,3,1,2,3","10,0,2,5,0", "15,15,5,3,3"})
    void division(long num1, byte den1, double value, long rnum, byte rden) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        mathCheckDouble(new Object[]{num1, den1, value, rnum, rden}, "division");
    }

    @Test
    @DisplayName("Проверка метода сравнения")
    void testEquals() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object h = getExpected(long.class);
        Object k = getExpected(byte.class);
        Object obj1 = getMoney(h, k);
        Object obj2 = getMoney(h, k);

        Method equals = MONEY_CLAZZ.getDeclaredMethod("equals", MONEY_CLAZZ);
        Assertions.assertTrue((Boolean) equals.invoke(obj1, obj2));
    }
}