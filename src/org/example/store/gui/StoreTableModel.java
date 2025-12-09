package org.example.store.gui;

import org.example.store.BakeryProduct;
import org.example.store.DairyProduct;
import org.example.store.Product;
import org.example.store.Store;

import javax.swing.table.AbstractTableModel;

public class StoreTableModel extends AbstractTableModel {
    private Store store;
    private final String[] columnNames = {"Тип", "Название", "Цена (руб)", "Спец. свойство"};

    public StoreTableModel(Store store) {
        this.store = store;
    }

    public void setStore(Store store) {
        this.store = store;
        fireTableDataChanged(); // уведомляем таблицу что данные обновились
    }

    @Override
    public int getRowCount() {
        return store != null ? store.getProducts().size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product p = store.getProducts().get(rowIndex);
        switch (columnIndex) {
            case 0: return p.getType();
            case 1: return p.getName();
            case 2: return p.getPrice();
            case 3:
                if (p instanceof BakeryProduct) {
                    return "Мука: " + ((BakeryProduct) p).getFlourType();
                } else if (p instanceof DairyProduct) {
                    return "Жирность: " + ((DairyProduct) p).getFatContent() + "%";
                }
                return "";
            default: return null;
        }
    }
}
