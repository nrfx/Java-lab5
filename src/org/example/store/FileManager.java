package org.example.store;
import java.io.*;
import java.util.*;

public class FileManager {
    public static boolean saveStore(Store store, String fileName) {
        if (store == null) return false;
        if (fileName == null || fileName.trim().isEmpty()) fileName = "store_data.txt";
        if (!fileName.endsWith(".txt")) fileName += ".txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("STORE:" + store.getName());
            for (Product product : store.getProducts()) {
                writer.println(productToFileLine(product));
            }
            System.out.println("Магазин сохранен в файл: " + fileName);
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
            return false;
        }
    }

    public static Store loadStore(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) fileName = "store_data.txt";
        if (!fileName.endsWith(".txt")) fileName += ".txt";
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Файл не найден.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String storeName = null;
            List<Product> products = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.startsWith("STORE:")) {
                    storeName = line.substring(6).trim();
                } else {
                    Product product = parseProductLine(line);
                    if (product != null) products.add(product);
                }
            }
            if (storeName == null) return null;
            Store store = new Store(storeName);
            for (Product p : products) store.addProduct(p);
            return store;
        } catch (Exception e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
            return null;
        }
    }

    private static String productToFileLine(Product product) {
        if (product instanceof BakeryProduct) {
            BakeryProduct b = (BakeryProduct) product;
            return "BAKERY|" + b.getName() + "|" + b.getPrice() + "|" + b.getFlourType();
        } else if (product instanceof DairyProduct) {
            DairyProduct d = (DairyProduct) product;
            return "DAIRY|" + d.getName() + "|" + d.getPrice() + "|" + d.getFatContent();
        }
        return "";
    }

    private static Product parseProductLine(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length != 4) return null;
            String type = parts[0].trim();
            String name = parts[1].trim();
            double price = Double.parseDouble(parts[2].trim());

            switch (type) {
                case "BAKERY":
                    String flour = parts[3].trim();
                    return new BakeryProduct(name, price, flour);
                case "DAIRY":
                    double fat = Double.parseDouble(parts[3].trim());
                    return new DairyProduct(name, price, fat);
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}