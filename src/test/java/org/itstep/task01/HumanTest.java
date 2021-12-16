package org.itstep.task01;

import org.itstep.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.itstep.TestUtil.*;

public class HumanTest {

    public static final String CLASS_NAME = "org.itstep.task01.Human";

    public static final Class<?> HUMAN_CLAZZ;

    static {
        Class<?> cls;
        try {
            cls = Class.forName(CLASS_NAME);
        } catch (ClassNotFoundException ex) {
            cls = null;
        }
        HUMAN_CLAZZ = cls;
    }

    @Test
    @Order(1)
    @DisplayName("Проверка наличия класса Human")
    void classExists() {
        isPublicNoStaticNoFinalNoAbstractClass(HUMAN_CLAZZ, CLASS_NAME);
    }

    @Order(2)
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка закрытого поля")
    @ValueSource(strings = {"fullName", "dateOfBirth", "phone", "city", "country", "address"})
    void fieldExists(String fieldName) throws NoSuchFieldException {
        hasPrivateNoStaticField(HUMAN_CLAZZ, fieldName);
    }

    @Order(2)
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка геттеров")
    @ValueSource(strings = {"getFullName", "getDateOfBirth", "getPhone", "getCity", "getCountry", "getAddress"})
    void getterExists(String getterName) throws NoSuchMethodException {
        hasPublicNoStaticMethod(HUMAN_CLAZZ, getterName);
    }

    @Order(3)
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка сеттеров")
    @ValueSource(strings = {"setFullName", "setDateOfBirth", "setPhone", "setCity", "setCountry", "setAddress"})
    void setterExists(String setterName) throws NoSuchMethodException {
        hasPublicNoStaticMethod(HUMAN_CLAZZ, setterName, String.class);
    }

    @Order(4)
    @ParameterizedTest(name = "{0}")
    @DisplayName("Проверка сеттеров/геттеров")
    @ValueSource(strings = {"fullName", "dateOfBirth", "phone", "city", "country", "address"})
    void setterGetter(String fieldName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> cls = Class.forName("org.itstep.task01.Human");
        Object human = cls.getDeclaredConstructor().newInstance();
        String setterName = String.format("set%c%s", Character.toUpperCase(fieldName.charAt(0)), fieldName.substring(1));
        String getterName = String.format("get%c%s", Character.toUpperCase(fieldName.charAt(0)), fieldName.substring(1));
        Method setter = cls.getDeclaredMethod(setterName, String.class);
        Method getter = cls.getDeclaredMethod(getterName);
        String expected = TestUtil.randomString();
        setter.invoke(human, expected);
        Object actual = getter.invoke(human);
        Assertions.assertEquals(expected, actual);
    }

    @Order(5)
    @Test
    @DisplayName("Проверка Конструкторов")
    void constructors() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> cls = Class.forName(CLASS_NAME);
        Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
        Assertions.assertEquals(2, declaredConstructors.length);

        Class<String> str = String.class;
        Constructor<?> ctor = cls.getDeclaredConstructor(str, str, str, str, str, str);

        String fullName = TestUtil.randomString();
        String dateOfBirth = TestUtil.randomString();
        String phone = TestUtil.randomString();
        String city = TestUtil.randomString();
        String country = TestUtil.randomString();
        String address = TestUtil.randomString();

        Object human = ctor.newInstance(fullName, dateOfBirth, phone, city, country, address);

        Method getFullName = cls.getDeclaredMethod("getFullName");
        Assertions.assertEquals(fullName, getFullName.invoke(human));

        Method getDateOfBirth = cls.getDeclaredMethod("getDateOfBirth");
        Assertions.assertEquals(dateOfBirth, getDateOfBirth.invoke(human));

        Method getPhone = cls.getDeclaredMethod("getPhone");
        Assertions.assertEquals(phone, getPhone.invoke(human));

        Method getCity = cls.getDeclaredMethod("getCity");
        Assertions.assertEquals(city, getCity.invoke(human));

        Method getCountry = cls.getDeclaredMethod("getCountry");
        Assertions.assertEquals(country, getCountry.invoke(human));

        Method getAddress = cls.getDeclaredMethod("getAddress");
        Assertions.assertEquals(address, getAddress.invoke(human));
    }
}
