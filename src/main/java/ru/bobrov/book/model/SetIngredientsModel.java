package ru.bobrov.book.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Rabbik on 19.04.2017.
 * Модель представления таблицы списка ингредиентов
 */
public class SetIngredientsModel extends AbstractTableModel {

    private final int column = 1;
    private ArrayList<String> listTable;

    public SetIngredientsModel(ArrayList<String> arr) {
        listTable = arr;
    }

    @Override
    public String getColumnName(int column) {
        return "Выбрать ингредиенты";
    }

    public int getRowCount() {
        return listTable.size();
    }

    public int getColumnCount() {
        return column;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return listTable.get(rowIndex);
    }


    public void deleteString(int columnIndex) {
        listTable.remove(columnIndex);
    }

    public void addString(String ingredientName) {
        listTable.add(ingredientName);
    }

    public void setListTable(ArrayList<String> listTable) {
        this.listTable = listTable;
    }
}
