package org.example.store.gui;

import org.example.store.BakeryProduct;
import org.example.store.DairyProduct;
import org.example.store.FileManager;
import org.example.store.Product;
import org.example.store.Store;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class StoreController {

    public void loadDemoStore(StoreGUI view) {
        Store store = new Store("Пятерочка (Демо)");
        store.addProduct(new BakeryProduct("Батон Нарезной", 50, "Пшеничная"));
        store.addProduct(new DairyProduct("Молоко Домик", 120, 3.2));
        store.addProduct(new BakeryProduct("Бородинский", 60, "Ржаная"));

        view.setStore(store);
        view.updateView();
    }

    public void addProduct(StoreGUI view) {
        ProductDialog dialog = new ProductDialog(view);
        dialog.setVisible(true);
        Product p = dialog.getResult();
        if (p != null) {
            view.getStore().addProduct(p);
            view.updateView();
        }
    }

    public void updateProduct(StoreGUI view) {
        JTable table = view.getTable();
        Store store = view.getStore();
        int selectedRow = table.getSelectedRow();

        if (selectedRow >= 0) {
            Product p = store.getProducts().get(selectedRow);
            JTextField nameField = new JTextField(p.getName());
            JTextField priceField = new JTextField(String.valueOf(p.getPrice()));
            Object[] message = {"Новое название:", nameField, "Новая цена:", priceField};

            int option = JOptionPane.showConfirmDialog(view, message, "Изменить товар", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    double newPrice = Double.parseDouble(priceField.getText());
                    store.updateProduct(selectedRow, nameField.getText(), newPrice);
                    view.updateView();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Ошибка: Цена должна быть числом!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(view, "Сначала выберите товар в таблице!");
        }
    }

    public void removeProduct(StoreGUI view) {
        JTable table = view.getTable();
        Store store = view.getStore();
        int selectedRow = table.getSelectedRow();

        if (selectedRow >= 0) {
            store.removeProduct(selectedRow);
            view.updateView();
        } else {
            JOptionPane.showMessageDialog(view, "Сначала выберите товар в таблице!");
        }
    }

    public void findProduct(StoreGUI view) {
        String searchName = JOptionPane.showInputDialog(view, "Введите название товара для поиска:");
        if (searchName != null && !searchName.trim().isEmpty()) {
            Product found = view.getStore().findProduct(searchName);
            if (found != null) {
                JOptionPane.showMessageDialog(view, "Товар найден!\n" + found.toString(), "Результат", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Товар с таким названием не найден.", "Результат", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void checkAllProducts(StoreGUI view) {
        view.getStore().checkAllProducts();
        JOptionPane.showMessageDialog(view, "Ревизия проведена успешно!\n(Подробности выведены в консоль IDE)");
    }

    public void sellAllProducts(StoreGUI view) {
        view.getStore().sellAllProducts();
        JOptionPane.showMessageDialog(view, "Все товары успешно проданы!\n(Подробности выведены в консоль IDE)");
    }

    public void callSpecialMethod(StoreGUI view) {
        JTable table = view.getTable();
        Store store = view.getStore();
        int selectedRow = table.getSelectedRow();

        if (selectedRow >= 0) {
            Product p = store.getProducts().get(selectedRow);
            if (p instanceof BakeryProduct) {
                ((BakeryProduct) p).toast();
                JOptionPane.showMessageDialog(view, "Спец. метод:\nПодсушиваем '" + p.getName() + "' в тостере.");
            } else if (p instanceof DairyProduct) {
                ((DairyProduct) p).ferment();
                JOptionPane.showMessageDialog(view, "Спец. метод:\n'" + p.getName() + "' оставлен для закваски.");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Сначала выберите товар в таблице для спец. действия!");
        }
    }

    public void saveToFile(StoreGUI view) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            FileManager.saveStore(view.getStore(), path);
            JOptionPane.showMessageDialog(view, "Успешно сохранено!");
        }
    }

    public void loadFromFile(StoreGUI view) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Текстовые файлы", "txt"));
        if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            Store loaded = FileManager.loadStore(chooser.getSelectedFile().getAbsolutePath());
            if (loaded != null) {
                view.setStore(loaded);
                view.updateView();
            } else {
                JOptionPane.showMessageDialog(view, "Ошибка загрузки файла!");
            }
        }
    }
}