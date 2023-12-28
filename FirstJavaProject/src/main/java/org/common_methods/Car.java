package org.common_methods;

import org.json.JSONObject;

public class Car {
    private final String color;

    private final String brand;

    private final int price;

    public Car(String color, String brand, int price) {
        this.color = color;
        this.brand = brand;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        Car aCar = (Car) obj;
        if (this.brand == null || aCar == null) {
            return false;
        } else {
            return this.brand.equals(aCar.brand) && this.color.equals(aCar.color);
        }
    }

    @Override
    protected Car clone() throws CloneNotSupportedException {
        return new Car(color, brand, price);
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("color", color);
        jsonObject.put("brand", brand);
        jsonObject.put("price", price);
        return jsonObject.toString();
    }

    String getAddress() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        String objAsString = color + "." + brand + "." + price;
        byte[] objAsBytes = objAsString.getBytes();

        int hashCode = 0;
        for (byte b : objAsBytes) {
            hashCode += b;
        }
        return hashCode;
    }

}
