package ru.bobrov.book;

import ru.bobrov.book.controllers.Cash;
import ru.bobrov.book.gui.AddNewIngredient;
import ru.bobrov.book.gui.AddNewReceptTable;
import ru.bobrov.book.gui.MainTable;
import ru.bobrov.book.gui.RecipesTable;


import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Rabbik on 17.04.2017.
 * Запускает приложение
 */
public class Main {
    public static void main(String[] args) throws Exception {
        MainTable table = new MainTable();
        Cash.getInstance().addObserver(table);
        table.go();
    }
}
