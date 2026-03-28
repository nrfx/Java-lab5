package org.example.store;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private String name;
    private List<Product> products;

    public Store(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Добавлен товар: " + product.getName());
    }

    public boolean removeProduct(int index) {
        if (index >= 0 && index < products.size()) {
            Product removed = products.remove(index);
            System.out.println("Удален товар: " + removed.getName());
            Product.decrementProductCount();
            return true;
        }
        return false;
    }

    public boolean updateProduct(int index, String newName, double newPrice) {
        if (index >= 0 && index < products.size()) {
            Product product = products.get(index);
            product.setName(newName);
            product.setPrice(newPrice);
            System.out.println("Товар обновлен: " + product.getName());
            return true;
        }
        return false;
    }

    public Product findProduct(String name) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public void displayAllProducts() {
        System.out.println("\nТовары в магазине '" + name + "' \n");
        if (products.isEmpty()) {
            System.out.println("Полки пусты!");
            return;
        }
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }
    }

    public void sellAllProducts() {
        System.out.println("\nМагазин '" + name + "' открывает продажи!");
        if (products.isEmpty()) {
            System.out.println("Нет товаров для продажи!");
            return;
        }
        for (Product product : products) {
            product.consume();
        }
        System.out.println("ВСЕ ПРОДАНО! КАССА ПОЛНА!\n");
    }

    public void checkAllProducts() {
        System.out.println("\nРевизия товаров");
        for (Product product : products) {
            product.checkQuality();
        }
    }

    public String getName() { return name; }
    public List<Product> getProducts() { return products; }
}