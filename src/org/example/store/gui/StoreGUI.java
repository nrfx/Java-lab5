package org.example.store.gui;
import org.example.store.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreGUI extends JFrame {
    private Store store;
    private JTable table;
    private StoreTableModel tableModel;
    private StoreController controller; // ссылка на контроллер

    public StoreGUI() {
        this.store = new Store("Новый Магазин");
        this.controller = new StoreController();

        setTitle("Управление магазином: " + store.getName());
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        setupMenuBar();

        tableModel = new StoreTableModel(store);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

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

        // привязываем кнопки к методам контроллера, передавая текущее окно
        btnDemo.addActionListener(e -> controller.loadDemoStore(this));
        btnAdd.addActionListener(e -> controller.addProduct(this));
        btnUpdate.addActionListener(e -> controller.updateProduct(this));
        btnRemove.addActionListener(e -> controller.removeProduct(this));
        btnFind.addActionListener(e -> controller.findProduct(this));
        btnCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.checkAllProducts(StoreGUI.this);
            }
        });
        btnSellAll.addActionListener(e -> controller.sellAllProducts(this));
        btnSpecial.addActionListener(e -> controller.callSpecialMethod(this));
        btnSave.addActionListener(e -> controller.saveToFile(this));
        btnLoad.addActionListener(e -> controller.loadFromFile(this));

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

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem openItem = new JMenuItem("Открыть...");
        JMenuItem saveItem = new JMenuItem("Сохранить...");

        // меню тоже использует методы контроллера
        openItem.addActionListener(e -> controller.loadFromFile(this));
        saveItem.addActionListener(e -> controller.saveToFile(this));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    // методы для связи контроллера с view
    public void updateView() {
        tableModel.setStore(store);
        setTitle("Управление магазином: " + store.getName());
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public JTable getTable() {
        return table;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StoreGUI().setVisible(true);
        });
    }
}