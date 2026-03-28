package org.example.store;

public class BakeryProduct extends Product {
    private String flourType;

    public BakeryProduct(String name, double price, String flourType) {
        super(name, price);
        this.flourType = flourType;
    }

    @Override
    public void consume() {
        System.out.println(getName() + " хрустит при откусывании! (Мука: " + flourType + ")");
    }

    @Override
    public String getType() {
        return "Выпечка";
    }

    @Override
    public String getStorageInfo() {
        return "Хранить в сухом месте, срок годности 3 суток";
    }

    public void toast() { // Уникальный метод
        System.out.println("Подсушиваем " + getName() + " в тостере.");
    }

    public String getFlourType() { return flourType; }
    public void setFlourType(String flourType) { this.flourType = flourType; }

    @Override
    public String toString() {
        return super.toString() + String.format(", Мука: %s", flourType);
    }
}