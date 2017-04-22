package ru.bobrov.book.gui;

import ru.bobrov.book.controllers.Cash;
import ru.bobrov.book.model.ListTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rabbik on 17.04.2017.
 * Первая и основная форма
 * Отображает список сохраненных рецептов блюд
 */
public class MainTable implements Observer {

    private JTable listTable;
    private ListTableModel listTableModel;
    private JTextField searchDishName;
    private JComboBox comboBoxIngredients;

    @Override
    public void update(Observable o, Object arg) {
        listTableModel.setDataArrayList(Cash.getInstance().getListTable());
        comboBoxIngredients.addItem(Cash.getInstance().arrayToMasIngredients()[Cash.getInstance().arrayToMasIngredients().length-1]);
        listTable.updateUI();

    }

    public void go () {

        JFrame frame = new JFrame("Список рецептов");
        frame.setSize(new Dimension(600, 400));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Cash.getInstance().saveData();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        listTableModel = new ListTableModel(Cash.getInstance().getListTable());
        listTable = new JTable(listTableModel);
        listTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = listTable.rowAtPoint(e.getPoint());
                    RecipesTable recipesTable = new RecipesTable();
                    recipesTable.seeRecept(Cash.getInstance().searchDish((String)listTableModel.getValueAt(row, 1)));
                }
            }
        });
        JScrollPane listTableScrollPage = new JScrollPane(listTable);
        listTableScrollPage.setPreferredSize(new Dimension(400, 400));

        //Панель поиска
        JButton buttonSearch = new JButton("Поиск");
        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search;

                if (searchDishName.getText() == null || searchDishName.getText().equals("")) {
                    search = (String) comboBoxIngredients.getSelectedItem();
                    ArrayList<String[]> copyListTable = new ArrayList<>(Cash.getInstance().getListTable());
                    for (String[] s : Cash.getInstance().getListTable()) {
                        boolean trueDish = false;
                        for ( String[] sIng : Cash.getInstance().searchDish(s[1]).getComposition()) {
                            if (sIng[0].equals(search)) {
                                trueDish = true;
                                break;
                            }
                        }
                        if(!trueDish){
                            copyListTable.remove(s);
                        }
                    }
                    listTableModel.setDataArrayList(copyListTable);
                    listTable.updateUI();
                }
                else {
                    search = searchDishName.getText();
                    ArrayList<String[]> copyListTable = new ArrayList<>(Cash.getInstance().getListTable());
                    for (String[] s : Cash.getInstance().getListTable())
                        if (!search.equals(s[1]))
                            copyListTable.remove(s);
                    listTableModel.setDataArrayList(copyListTable);
                    listTable.updateUI();
                }


            }
        });
        JButton endSearch = new JButton("Сбросить фильтр");
        endSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listTableModel.setDataArrayList(Cash.getInstance().getListTable());
                listTable.updateUI();
            }
        });
        searchDishName = new JTextField();
        JLabel serchDishNameLabel = new JLabel("Введите название блюда");
        JLabel searchIngredientLabel = new JLabel("Выберите компонент");
        comboBoxIngredients = new javax.swing.JComboBox(Cash.getInstance().arrayToMasIngredients());
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        JPanel northSearchPanel = new JPanel();
        northSearchPanel.setLayout(new BoxLayout(northSearchPanel, BoxLayout.Y_AXIS));
        northSearchPanel.add(serchDishNameLabel);
        northSearchPanel.add(searchDishName);
        northSearchPanel.add(searchIngredientLabel);
        northSearchPanel.add(comboBoxIngredients);
        northSearchPanel.add(buttonSearch);
        northSearchPanel.add(endSearch);
        searchPanel.add(northSearchPanel, BorderLayout.NORTH);

        //Нижняя панель
            JButton addReceptButton = new JButton("Добавить новый рецепт");
            addReceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddNewReceptTable addNewReceptTable = new AddNewReceptTable();
                    addNewReceptTable.addRecept();
                    Cash.getInstance().addObserver(addNewReceptTable);
                }
            });


        frame.getContentPane().add(BorderLayout.CENTER, listTableScrollPage);
        frame.getContentPane().add(BorderLayout.EAST, searchPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, addReceptButton);


        frame.setVisible(true);
        frame.pack();
    }
}
