package org.example.store;

public abstract class Product implements Storable {
    private String name;
    private double price;
    private static int productCount = 0;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        productCount++;
    }

    public abstract void consume(); // Аналог play()
    public abstract String getType();

    @Override
    public void checkQuality() {
        System.out.println(name + " проходит проверку качества...");
    }

    @Override
    public String getStorageInfo() {
        return "Стандартные условия хранения";
    }

    public final void displayBasicInfo() {
        System.out.println("Товар: " + name + ", цена: " + price + " руб.");
    }

    // Геттеры и сеттеры
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public static int getProductCount() { return productCount; }
    public static void decrementProductCount() {
        if (productCount > 0) productCount--;
    }
    public static void resetProductCount() { productCount = 0; }

    @Override
    public String toString() {
        return String.format("%s: %s, Цена: %.2f руб", getType(), name, price);
    }
}