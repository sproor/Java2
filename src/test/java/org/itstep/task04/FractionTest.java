package org.itstep.task04;

import org.itstep.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.*;

import static org.itstep.TestUtil.hasPrivateNoStaticField;
import static org.itstep.TestUtil.hasPublicNoStaticMethod;

class FractionTest {

    public static final String CLASS_NAME = "org.itstep.task04.Fraction";

    public static final Class<?> FRACTION_CLAZZ;

    static {
        Class<?> cls;
        try {
            cls = Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException ex) {
            cls = null;
        }
        FRACTION_CLAZZ = cls;
    }

    @Test
    @Order(1)
    @DisplayName("Проверка наличия класса Fraction")
    void classExists() {
        Assertions.assertNotNull(FRACTION_CLAZZ, "Класс " + CLASS_NAME + " не найден");
    }

    @Order(2)
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка закрытых полей")
    @ValueSource(strings = {"numerator", "denominator"})
    void privateFieldExists(String fieldName) throws NoSuchFieldException {
        hasPrivateNoStaticField(FRACTION_CLAZZ, fieldName);
    }

    @Order(2)
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка геттеров")
    @ValueSource(strings = {"getNumerator", "getDenominator"})
    void publicGetterExists(String getterName) throws NoSuchMethodException {
        hasPublicNoStaticMethod(FRACTION_CLAZZ, getterName);
    }

    @Order(3)
    @ParameterizedTest(name = "{1}")
    @DisplayName("Проверка сеттеров")
    @CsvSource({"int,setNumerator", "int,setDenominator"})
    void publicSetterExists(Class<?> clazz, String setterName) throws NoSuchMethodException {
        hasPublicNoStaticMethod(FRACTION_CLAZZ, setterName, clazz);
    }

    @Order(4)
    @ParameterizedTest(name = "{1}")
    @DisplayName("Проверка сеттеров/геттеров")
    @CsvSource({"int,numerator", "int,denominator"})
    void setterGetterWork(Class<?> clazz, String fieldName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object obj = FRACTION_CLAZZ.getDeclaredConstructor().newInstance();

        String setterName = String.format("set%c%s", Character.toUpperCase(fieldName.charAt(0)), fieldName.substring(1));
        String getterName = String.format("get%c%s", Character.toUpperCase(fieldName.charAt(0)), fieldName.substring(1));

        Method setter = FRACTION_CLAZZ.getDeclaredMethod(setterName, clazz);
        Method getter = FRACTION_CLAZZ.getDeclaredMethod(getterName);

        Object expected = TestUtil.getExpected(clazz);

        setter.invoke(obj, expected);

        Object actual = getter.invoke(obj);
        Assertions.assertEquals(expected, actual);
    }

    @Order(5)
    @Test
    @DisplayName("Проверка Конструкторов")
    void constructors() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] declaredConstructors = FRACTION_CLAZZ.getDeclaredConstructors();
        Assertions.assertEquals(2, declaredConstructors.length);

        Constructor<?> ctor = FRACTION_CLAZZ.getDeclaredConstructor(int.class, int.class);

        Object numerator = TestUtil.getExpected(int.class);
        Object denominator = TestUtil.getExpected(int.class);

        Object obj = ctor.newInstance(numerator, denominator);

        Method getNumerator = FRACTION_CLAZZ.getDeclaredMethod("getNumerator");
        Assertions.assertEquals(numerator, getNumerator.invoke(obj));

        Method getDenominator = FRACTION_CLAZZ.getDeclaredMethod("getDenominator");
        Assertions.assertEquals(denominator, getDenominator.invoke(obj));
    }

    Object getFraction(Object num, Object den) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> ctor = FRACTION_CLAZZ.getDeclaredConstructor(int.class, int.class);
        return ctor.newInstance(num, den);
    }

    void mathCheck(int[] args, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Object first = getFraction(args[0], args[1]);
        Object second = getFraction(args[2], args[3]);

        Method additionMethod = FRACTION_CLAZZ.getDeclaredMethod(methodName, FRACTION_CLAZZ);
        Object result = additionMethod.invoke(first, second);
        Assertions.assertEquals(FRACTION_CLAZZ, result.getClass(), "Метод "+methodName+" должен возвращать Fraction");

        Method getNumerator = FRACTION_CLAZZ.getDeclaredMethod("getNumerator");
        Method getDenominator = FRACTION_CLAZZ.getDeclaredMethod("getDenominator");

        Assertions.assertEquals(args[4], getNumerator.invoke(result), "Неверный числитель");
        Assertions.assertEquals(args[5], getDenominator.invoke(result), "Неверный знаменатель");
    }

    @Order(6)
    @DisplayName("Проверка метода сложения")
    @ParameterizedTest(name = "{0}/{1}+{2}/{3}={4}/{5}")
    @CsvSource({"1,3,1,3,2,3","2,5,2,5,4,5", "1,2,1,2,1,1"})//числитель/знаменатель + числитель/знаменатель = числитель/занменатель
    void addition(int num1, int den1, int num2, int den2, int rnum, int rden)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        mathCheck(new int[]{num1, den1, num2, den2, rnum, rden}, "addition");
    }

    @Order(7)
    @DisplayName("Проверка метода вычитания")
    @ParameterizedTest(name = "{0}/{1}-{2}/{3}={4}/{5}")
    @CsvSource({"2,3,1,3,1,3","3,5,2,5,1,5", "5,4,1,4,1,1"})//числитель/знаменатель - числитель/знаменатель = числитель/занменатель
    void subtraction(int num1, int den1, int num2, int den2, int rnum, int rden) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        mathCheck(new int[]{num1, den1, num2, den2, rnum, rden}, "subtraction");
    }

    @Order(8)
    @DisplayName("Проверка метода умножения")
    @ParameterizedTest(name = "{0}/{1}*{2}/{3}={4}/{5}")
    @CsvSource({"2,3,1,3,2,9","3,5,2,5,6,25", "5,4,1,4,5,16"})//числитель/знаменатель * числитель/знаменатель = числитель/занменатель
    void multiplication(int num1, int den1, int num2, int den2, int rnum, int rden) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        mathCheck(new int[]{num1, den1, num2, den2, rnum, rden}, "multiplication");
    }

    @Order(9)
    @DisplayName("Проверка метода деления")
    @ParameterizedTest(name = "{0}/{1}/{2}/{3}={4}/{5}")
    @CsvSource({"2,3,1,3,2,1","3,5,2,5,3,2", "5,4,1,4,5,1"})//числитель/знаменатель / числитель/знаменатель = числитель/занменатель
    void division(int num1, int den1, int num2, int den2, int rnum, int rden) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        mathCheck(new int[]{num1, den1, num2, den2, rnum, rden}, "division");
    }
}