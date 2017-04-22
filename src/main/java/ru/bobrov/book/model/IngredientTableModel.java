package ru.bobrov.book.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


/**
 * Created by Rabbik on 18.04.2017.
 * Модель представления таблицы списка ингредиентов с их количеством
 */
public class IngredientTableModel extends AbstractTableModel {
    private int columnCount = 2;
    private ArrayList<String[]> listTable = new ArrayList<>();




    public int getRowCount() {
        return listTable.size();
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {

        String [] stringResult = listTable.get(rowIndex);
        return stringResult[columnIndex];

    }

    public void deleteDate(int indexDelete) {
        listTable.remove(indexDelete);
    }

    @Override
    public String getColumnName(int column) {
        String result = "";
        switch (column) {
            case 0:
                result = "Выбранные ингредиенты";
                break;
            case 1:
                result = "Масса, грамм";
                break;

        }
        return result;
    }

    public void addString(String name) {
        String [] str = {name, ""};
        listTable.add(str);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            listTable.get(rowIndex)[1] = String.valueOf(Long.parseLong((String) aValue));
        } catch (NumberFormatException e) {
            e.getMessage();
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        boolean result = false;
        if (columnIndex == 1)
            result = true;
        return result;

    }

    public ArrayList<String[]> getListTable() {
        return listTable;
    }

    public void setListTable(ArrayList<String[]> listTable) {
        this.listTable = listTable;
    }

    public int MassaIngredients() {
        int result = 0;
        for (String[] s : listTable) {
            try {
                result += Integer.parseInt(s[1]);
            } catch (NumberFormatException nfe) {
                result = 0;
            }
        }
        return result;
    }
}
