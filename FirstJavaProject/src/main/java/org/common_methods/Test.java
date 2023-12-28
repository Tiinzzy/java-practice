package org.common_methods;

public class Test {

    static void test() {
        Car c = new Car("", "", 1);
        System.out.println(c.toString());
    }
    public static void main(String[] args) throws CloneNotSupportedException {
        Car tinaCar = new Car("Red", "Hummer", 10);
        Car shahinCar = new Car("Red", "Hummer", 20);
        Car kamranCar = new Car("White", "Toyota", 15);

        System.out.println(1);
        System.out.println(kamranCar.equals(shahinCar));

        System.out.println(tinaCar.toString());
        System.out.println(tinaCar.getAddress());

        System.out.println(2);
        Car tinaCloneCar = tinaCar.clone();
        System.out.println(tinaCloneCar.toString());
        System.out.println(tinaCloneCar.getAddress());

        System.out.println(3);
        System.out.println(tinaCloneCar.equals(tinaCar));
        System.out.println(tinaCar == tinaCloneCar);

        System.out.println(4);
        System.out.println(tinaCloneCar.hashCode());
        System.out.println(tinaCar.hashCode());


        test();





    }
}
