package org.example.store.gui;

import org.example.store.BakeryProduct;
import org.example.store.DairyProduct;
import org.example.store.FileManager;
import org.example.store.Product;
import org.example.store.Store;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreGUI extends JFrame {
    private Store store;
    private JTable table;
    private StoreTableModel tableModel;

    public StoreGUI() {
        // пустой магазин при старте
        store = new Store("Новый Магазин");

        setTitle("Управление магазином: " + store.getName());
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); // Главный компоновщик с отступами

        // верхнее меню
        setupMenuBar();

        tableModel = new StoreTableModel(store);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // правые кнопки
        JPanel actionPanel = new JPanel(new GridLayout(10, 1, 5, 5));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnDemo = new JButton("Загрузить Демо-магазин");
        JButton btnAdd = new JButton("2. Добавить новый товар");
        JButton btnUpdate = new JButton("3. Изменить товар");
        JButton btnRemove = new JButton("4. Удалить товар");
        JButton btnFind = new JButton("5. Найти товар");
        JButton btnCheck = new JButton("6. Провести ревизию");
        JButton btnSellAll = new JButton("7. Продать все");
        JButton btnSpecial = new JButton("8. Вызвать спец. методы");
        JButton btnSave = new JButton("9. Сохранить в файл");
        JButton btnLoad = new JButton("10. Открыть из файла");

        // обработчики событий для кнопок

        btnDemo.addActionListener(e -> {
            store = new Store("Пятерочка (Демо)");
            store.addProduct(new BakeryProduct("Батон Нарезной", 50, "Пшеничная"));
            store.addProduct(new DairyProduct("Молоко Домик", 120, 3.2));
            store.addProduct(new BakeryProduct("Бородинский", 60, "Ржаная"));
            updateView();
        });

        btnAdd.addActionListener(e -> {
            ProductDialog dialog = new ProductDialog(this);
            dialog.setVisible(true);
            Product p = dialog.getResult();
            if (p != null) {
                store.addProduct(p);
                updateView();
            }
        });

        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Product p = store.getProducts().get(selectedRow);
                JTextField nameField = new JTextField(p.getName());
                JTextField priceField = new JTextField(String.valueOf(p.getPrice()));
                Object[] message = {"Новое название:", nameField, "Новая цена:", priceField};

                int option = JOptionPane.showConfirmDialog(this, message, "Изменить товар", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        double newPrice = Double.parseDouble(priceField.getText());
                        store.updateProduct(selectedRow, nameField.getText(), newPrice);
                        updateView();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Ошибка: Цена должна быть числом!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Сначала выберите товар в таблице!");
            }
        });

        btnRemove.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                    store.removeProduct(selectedRow);
                updateView();
            } else {
                JOptionPane.showMessageDialog(this, "Сначала выберите товар в таблице!");
            }
        });

        btnFind.addActionListener(e -> {
            String searchName = JOptionPane.showInputDialog(this, "Введите название товара для поиска:");
            if (searchName != null && !searchName.trim().isEmpty()) {
                Product found = store.findProduct(searchName);
                if (found != null) {
                    JOptionPane.showMessageDialog(this, "Товар найден!\n" + found.toString(), "Результат", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Товар с таким названием не найден.", "Результат", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

//        btnCheck.addActionListener(e -> {
//            store.checkAllProducts();
//            JOptionPane.showMessageDialog(this, "Ревизия проведена успешно!\n(Подробности выведены в консоль IDE)");
//        });

        btnCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                store.checkAllProducts();
                JOptionPane.showMessageDialog(StoreGUI.this, "Ревизия проведена успешно!\n(Подробности выведены в консоль IDE)");
            }
        });

        btnSellAll.addActionListener(e -> {
            store.sellAllProducts();
            JOptionPane.showMessageDialog(this, "Все товары успешно проданы!\n(Подробности выведены в консоль IDE)");
        });

        btnSpecial.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Product p = store.getProducts().get(selectedRow);
                if (p instanceof BakeryProduct) {
                    ((BakeryProduct) p).toast();
                    JOptionPane.showMessageDialog(this, "Спец. метод:\nПодсушиваем '" + p.getName() + "' в тостере.");
                } else if (p instanceof DairyProduct) {
                    ((DairyProduct) p).ferment();
                    JOptionPane.showMessageDialog(this, "Спец. метод:\n'" + p.getName() + "' оставлен для закваски.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Сначала выберите товар в таблице для спец. действия!");
            }
        });

        btnSave.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                FileManager.saveStore(store, path);
                JOptionPane.showMessageDialog(this, "Успешно сохранено!");
            }
        });

        btnLoad.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Текстовые файлы", "txt"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Store loaded = FileManager.loadStore(chooser.getSelectedFile().getAbsolutePath());
                if (loaded != null) {
                    store = loaded;
                    tableModel.setStore(store);
                    setTitle("Управление магазином: " + store.getName());
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка загрузки файла!");
                }
            }
        });

        // добавил кнопки на панель
        actionPanel.add(btnDemo);
        actionPanel.add(btnAdd);
        actionPanel.add(btnUpdate);
        actionPanel.add(btnRemove);
        actionPanel.add(btnFind);
        actionPanel.add(btnCheck);
        actionPanel.add(btnSellAll);
        actionPanel.add(btnSpecial);
        actionPanel.add(btnSave);
        actionPanel.add(btnLoad);

        add(actionPanel, BorderLayout.EAST);
    }

    private void updateView() {
        tableModel.setStore(store);
        setTitle("Управление магазином: " + store.getName());
    }
//верхнее меню
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem openItem = new JMenuItem("Открыть...");
        JMenuItem saveItem = new JMenuItem("Сохранить...");

        openItem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Текстовые файлы", "txt"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Store loaded = FileManager.loadStore(chooser.getSelectedFile().getAbsolutePath());
                if (loaded != null) {
                    store = loaded;
                    tableModel.setStore(store);
                    setTitle("Управление магазином: " + store.getName());
                } else {
                    JOptionPane.showMessageDialog(this, "Ошибка загрузки файла!");
                }
            }
        });

        saveItem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getAbsolutePath();
                FileManager.saveStore(store, path);
                JOptionPane.showMessageDialog(this, "Успешно сохранено!");
            }
        });

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StoreGUI().setVisible(true);
        });
    }
}