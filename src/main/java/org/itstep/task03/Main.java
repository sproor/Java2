package org.itstep.task03;

import org.itstep.task02.City;

import java.util.ArrayList;

/**
 * Задание 3
 * <p>
 * Создайте класс «Страна» (Country).
 * <p>
 * Необходимо хранить в полях класса:
 * - название страны (name),
 * - название континента (continent),
 * - телефонный код страны (code),
 * - название столицы (capital - класс City),
 * - города страны (cities - массив City).
 * <p>
 * Реализуйте методы класса для ввода данных, вывода
 * данных, реализуйте доступ к отдельным полям через
 * методы класса.
 * <p>
 * Добавить метод addCity для добавления нового города (города хранятся в массиве cities)
 * <p>
 * Метод getInhabitants должен возвращать количество жителей во всех городах страны (перебрать все города
 * в массиве cities и получить сумму жителей всех городов)
 * <p>
 * Реализовать два конструктора: один по умолчанию, второй с параметрами:
 * - название страны (name),
 * - название континента (continent),
 * - телефонный код страны (code),
 * - название столицы (capital).
 * <p>
 * В этом классе должен быть агрегирован класс Города (City) из предыдущей задачи.
 * <p>
 * Класс должен находиться в отдельном файле в этом же пакете
 */
class Temp{
    private int  count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Temp(int count) {
        this.count = count;
    }
}
class Temp2{
    private ArrayList<Temp> temps;
    public int getCount(){
        int sum = 0;
        for (Temp temp:this.temps
        ) {
            sum+=(int)temp.getCount();
        }
        return sum;
    }
    public void addCount(Temp temp) {
        this.temps.add(temp);
    }

    @Override
    public String toString() {
        return "Temp2{" +
                "temps=" + temps +
                '}';
    }

}
public class Main {
    public static void main(String[] args) {
        // FIXME: здесь пример использования класса
        Temp temp1 = new Temp(3);
        Temp temp2 = new Temp(2);
        Temp temp3 = new Temp(5);

        Temp2 temp21 = new Temp2();
        temp21.addCount(temp1);
        System.out.println(temp21.toString());
    }
}
