package org.example.store;

public class Test {
    public static void runAllTests() {
        System.out.println("ТЕСТИРОВАНИЕ МАГАЗИНА\n");
        testInheritance();
        testPolymorphism();
        testEncapsulation();
        testInterface();
        testStaticCounter();
        testStore();
        testFileOperations();
        System.out.println("\nВСЕ ТЕСТЫ ПРОЙДЕНЫ УСПЕШНО!\n");
    }

    private static void testInheritance() {
        System.out.println("1. ТЕСТ НАСЛЕДОВАНИЯ:");
        BakeryProduct bread = new BakeryProduct("Test Bread", 50, "Rye");
        System.out.println("  Создан BakeryProduct, родитель: " + bread.getClass().getSuperclass().getSimpleName());
    }

    private static void testPolymorphism() {
        System.out.println("\n2. ТЕСТ ПОЛИМОРФИЗМА:");
        Product[] products = {
                new BakeryProduct("Poly Bread", 40, "Wheat"),
                new DairyProduct("Poly Milk", 80, 2.5)
        };
        for (Product p : products) {
            System.out.print("  ");
            p.consume();
        }
    }

    private static void testEncapsulation() {
        System.out.println("\n3. ТЕСТ ИНКАПСУЛЯЦИИ:");
        BakeryProduct p = new BakeryProduct("Old Name", 100, "None");
        p.setName("New Name");
        System.out.println("  Имя изменено через сеттер: " + p.getName());
    }

    private static void testInterface() {
        System.out.println("\n4. ТЕСТ ИНТЕРФЕЙСА:");
        Product p = new DairyProduct("Cheese", 500, 50);
        p.checkQuality();
        System.out.println("  Info: " + p.getStorageInfo());
    }

    private static void testStaticCounter() {
        System.out.println("\n5. ТЕСТ СЧЕТЧИКА:");
        int before = Product.getProductCount();
        new BakeryProduct("Count1", 1, "1");
        new DairyProduct("Count2", 1, 1);
        int after = Product.getProductCount();
        System.out.println("  Было: " + before + ", Стало: " + after + ", Разница: " + (after - before));
    }

    private static void testStore() {
        System.out.println("\n7. ТЕСТ МАГАЗИНА:");
        Store s = new Store("Test Store");
        s.addProduct(new BakeryProduct("B", 1, "F"));
        s.displayAllProducts();
        s.sellAllProducts();
    }

    private static void testFileOperations() {
        System.out.println("\n8. ТЕСТ ФАЙЛОВ:");
        Store s = new Store("File Store");
        s.addProduct(new DairyProduct("Milk", 90, 3.2));
        FileManager.saveStore(s, "test_store.txt");
        Store loaded = FileManager.loadStore("test_store.txt");
        System.out.println("  Загружено: " + (loaded != null ? loaded.getName() : "Ошибка"));
    }
}