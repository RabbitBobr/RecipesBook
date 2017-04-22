package ru.bobrov.book.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Rabbik on 17.04.2017.
 * Модель представления таблицы списка рецептов
 */
public class ListTableModel extends AbstractTableModel {

    private int columnCount = 3;
    private ArrayList<String []> dataArrayList;

    public ListTableModel(ArrayList<String []> arr) {
        dataArrayList = arr;
    }

    public int getRowCount() {
        return dataArrayList.size();
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {

        String [] stringResult = dataArrayList.get(rowIndex);
        return stringResult[columnIndex];

    }

    public void setDataArrayList(ArrayList<String[]> dataArrayList) {
        this.dataArrayList = dataArrayList;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String result = "";
            switch (columnIndex) {
                case 0: result = "Номер"; break;
                case 1: result = "Название блюда"; break;
                case 2: result = "Время приготовления"; break;
            }
        return result;
    }

    }
