package org.example.store.gui;

import org.example.store.BakeryProduct;
import org.example.store.DairyProduct;
import org.example.store.Product;

import javax.swing.*;
import java.awt.*;

public class ProductDialog extends JDialog {
    private JTextField nameField, priceField, specField;
    private JComboBox<String> typeBox;
    private Product resultProduct = null;

    public ProductDialog(JFrame parent) {
        super(parent, "Добавить/Изменить товар", true);
        setLayout(new BorderLayout());
        setSize(300, 200);
        setLocationRelativeTo(parent);

        // панель ввода данных - GridLayout делает окно резиновым
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        inputPanel.add(new JLabel("Тип товара:"));
        typeBox = new JComboBox<>(new String[]{"Выпечка", "Молочка"});
        inputPanel.add(typeBox);

        inputPanel.add(new JLabel("Название:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Цена:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        JLabel specLabel = new JLabel("Тип муки:");
        inputPanel.add(specLabel);
        specField = new JTextField();
        inputPanel.add(specField);

        // динамическая смена текста (событие)
        typeBox.addActionListener(e -> {
            if (typeBox.getSelectedIndex() == 0) specLabel.setText("Тип муки:");
            else specLabel.setText("Жирность (%):");
        });

        add(inputPanel, BorderLayout.CENTER);

        // панель кнопок (FlowLayout)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton okButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");

        okButton.addActionListener(e -> saveProduct());
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveProduct() {
        try { // исключения
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            String spec = specField.getText();

            if (typeBox.getSelectedIndex() == 0) {
                resultProduct = new BakeryProduct(name, price, spec);
            } else {
                double fat = Double.parseDouble(spec);
                resultProduct = new DairyProduct(name, price, fat);
            }
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ошибка ввода чисел! Проверьте цену или жирность.",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Product getResult() {
        return resultProduct;
    }
}