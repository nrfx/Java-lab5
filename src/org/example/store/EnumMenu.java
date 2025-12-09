package org.example.store;

public enum EnumMenu {
    // Главное меню
    CREATE_STORE("1", "Создать новый магазин"),
    USE_DEMO("2", "Открыть демо-магазин"),
    OPEN_FILE("3", "Открыть магазин из файла"),
    RUN_TESTS("4", "Запустить тесты"),
    EXIT("5", "Выход"),

    // Меню магазина
    SHOW_PRODUCTS("1", "Показать все товары"),
    ADD_PRODUCT("2", "Добавить новый товар"),
    UPDATE_PRODUCT("3", "Изменить товар"),
    REMOVE_PRODUCT("4", "Удалить товар"),
    FIND_PRODUCT("5", "Найти товар"),
    CHECK_ALL("6", "Провести ревизию (качество)"),
    SELL_ALL("7", "Начать торговлю (продать все)"),
    CALL_METHODS("8", "Вызвать спец. методы"),
    SAVE_TO_FILE("9", "Сохранить магазин в файл"),
    BACK_TO_MAIN("10", "Выйти в главное меню");

    private final String key;
    private final String description;

    EnumMenu(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() { return key; }
    public String getDescription() { return description; }

    public static EnumMenu fromKey(String key, EnumMenu[] commands) {
        for (EnumMenu command : commands) {
            if (command.getKey().equals(key)) return command;
        }
        return null;
    }

    public static void printMenu(EnumMenu[] commands) {
        for (EnumMenu command : commands) {
            System.out.printf("%s. %s%n", command.getKey(), command.getDescription());
        }
    }

    public static EnumMenu[] getMainMenuCommands() {
        return new EnumMenu[] { CREATE_STORE, USE_DEMO, OPEN_FILE, RUN_TESTS, EXIT };
    }

    public static EnumMenu[] getStoreMenuCommands() {
        return new EnumMenu[] { SHOW_PRODUCTS, ADD_PRODUCT, UPDATE_PRODUCT, REMOVE_PRODUCT,
                FIND_PRODUCT, CHECK_ALL, SELL_ALL, CALL_METHODS, SAVE_TO_FILE, BACK_TO_MAIN };
    }
}