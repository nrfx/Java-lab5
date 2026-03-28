package org.example.store;

public class DairyProduct extends Product {
    private double fatContent;

    public DairyProduct(String name, double price, double fatContent) {
        super(name, price);
        this.fatContent = fatContent;
    }

    @Override
    public void consume() {
        System.out.println(getName() + " выпит с удовольствием! (Жирность: " + fatContent + "%)");
    }

    @Override
    public String getType() {
        return "Молочка";
    }

    @Override
    public void checkQuality() {
        System.out.println(getName() + " проверяется на свежесть в лаборатории...");
    }

    public void ferment() { // Уникальный метод
        System.out.println(getName() + " оставлен для закваски.");
    }

    public double getFatContent() { return fatContent; }
    public void setFatContent(double fatContent) { this.fatContent = fatContent; }

    @Override
    public String toString() {
        return super.toString() + String.format(", Жирность: %.1f%%", fatContent);
    }
}