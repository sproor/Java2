package org.itstep.task03;

import org.itstep.TestUtil;
import org.itstep.task02.CityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.*;

import static org.itstep.TestUtil.*;

public class CountryTest {

    public static final String CLASS_NAME = "org.itstep.task03.Country";

    public static final Class<?> COUNTRY_CLAZZ;

    static {
        Class<?> cls;
        try {
            cls = Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException ex) {
            cls = null;
        }
        COUNTRY_CLAZZ = cls;
    }

    @Test
    @Order(1)
    @DisplayName("Проверка наличия класса Country")
    void classExists() {
        isPublicNoStaticNoFinalNoAbstractClass(COUNTRY_CLAZZ, CLASS_NAME);
    }

    @Order(2)
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка закрытых полей")
    @ValueSource(strings = {"name", "continent", "code", "capital", "cities"})
    void privateFieldExists(String fieldName) throws NoSuchFieldException {
        hasPrivateNoStaticField(COUNTRY_CLAZZ, fieldName);
    }

    @Order(2)
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка геттеров")
    @ValueSource(strings = {"getName", "getContinent", "getCode", "getInhabitants", "getCapital", "getCities"})
    void publicGetterExists(String getterName) throws NoSuchMethodException {
        hasPublicNoStaticMethod(COUNTRY_CLAZZ, getterName);
    }

    @Order(3)
    @ParameterizedTest(name = "{1}")
    @DisplayName("Проверка сеттеров")
    @CsvSource({"java.lang.String,setName", "java.lang.String,setContinent", "java.lang.String,setCode",
            "org.itstep.task02.City,setCapital", "org.itstep.task02.City,addCity"})
    void publicSetterExists(Class<?> clazz, String setterName) throws NoSuchMethodException {
        hasPublicNoStaticMethod(COUNTRY_CLAZZ, setterName, clazz);
    }

    @Order(4)
    @ParameterizedTest(name = "{1}")
    @DisplayName("Проверка сеттеров/геттеров")
    @CsvSource({"java.lang.String,name", "java.lang.String,continent", "java.lang.String,code",
            "org.itstep.task02.City,capital"})
    void setterGetterWork(Class<?> clazz, String fieldName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> cls = Class.forName(CLASS_NAME);
        Object obj = cls.getDeclaredConstructor().newInstance();

        String setterName = String.format("set%c%s", Character.toUpperCase(fieldName.charAt(0)), fieldName.substring(1));
        String getterName = String.format("get%c%s", Character.toUpperCase(fieldName.charAt(0)), fieldName.substring(1));

        Method setter = cls.getDeclaredMethod(setterName, clazz);
        Method getter = cls.getDeclaredMethod(getterName);
        Object expected = TestUtil.getExpected(clazz);
        setter.invoke(obj, expected);
        Object actual = getter.invoke(obj);
        Assertions.assertEquals(expected, actual);
    }

    @Order(5)
    @Test
    @DisplayName("Проверка метода добавления города")
    void addCity() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> cls = Class.forName(CLASS_NAME);
        Object obj = cls.getDeclaredConstructor().newInstance();

        Class<?> argClass = Class.forName(CityTest.CLASS_NAME);
        Method setter = cls.getDeclaredMethod("addCity", argClass);

        setter.invoke(obj, TestUtil.getExpected(argClass));
        setter.invoke(obj, TestUtil.getExpected(argClass));
        setter.invoke(obj, TestUtil.getExpected(argClass));
    }

    @Order(6)
    @Test
    @DisplayName("Проверка метода подсчета количества жителей")
    void inhabitantsCount() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> cls = Class.forName(CLASS_NAME);
        Object obj = cls.getDeclaredConstructor().newInstance();

        Class<?> argClass = Class.forName(CityTest.CLASS_NAME);
        Method addCity = cls.getDeclaredMethod("addCity", argClass);

        Object city1 = TestUtil.getExpected(argClass);
        addCity.invoke(obj, city1);
        Object city2 = TestUtil.getExpected(argClass);
        addCity.invoke(obj, city2);
        Object city3 = TestUtil.getExpected(argClass);
        addCity.invoke(obj, city3);

        Method getCityInhabitants = argClass.getDeclaredMethod("getInhabitants");
        int expectedInhabitants = 0;
        expectedInhabitants += (int)getCityInhabitants.invoke(city1);
        expectedInhabitants += (int)getCityInhabitants.invoke(city2);
        expectedInhabitants += (int)getCityInhabitants.invoke(city3);

        Method getInhabitants = cls.getDeclaredMethod("getInhabitants");
        Object inhabitants = getInhabitants.invoke(obj);
        Assertions.assertEquals(expectedInhabitants, inhabitants);
    }

    @Order(7)
    @Test
    @DisplayName("Проверка Конструкторов")
    void constructors() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class<?> cls = Class.forName(CLASS_NAME);
        Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
        Assertions.assertEquals(2, declaredConstructors.length);

        Class<String> stringClass = String.class;
        Class<?> cityClass = Class.forName(CityTest.CLASS_NAME);
        Constructor<?> ctor = cls.getDeclaredConstructor(stringClass, stringClass, stringClass, cityClass);

        Object name = TestUtil.getExpected(stringClass);
        Object continent = TestUtil.getExpected(stringClass);
        Object code = TestUtil.getExpected(stringClass);
        Object capital = TestUtil.getExpected(cityClass);

        Object obj = ctor.newInstance(name, continent, code, capital);

        Method getName = cls.getDeclaredMethod("getName");
        Assertions.assertEquals(name, getName.invoke(obj));

        Method getContinent = cls.getDeclaredMethod("getContinent");
        Assertions.assertEquals(continent, getContinent.invoke(obj));

        Method getCode = cls.getDeclaredMethod("getCode");
        Assertions.assertEquals(code, getCode.invoke(obj));

        Method getCapital = cls.getDeclaredMethod("getCapital");
        Assertions.assertSame(capital, getCapital.invoke(obj));
    }
}
