package ru.bobrov.book.gui;

import ru.bobrov.book.Dish;
import ru.bobrov.book.controllers.Cash;
import ru.bobrov.book.model.IngredientTableModel;
import ru.bobrov.book.model.SetIngredientsModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;


/**
 * Created by Rabbik on 18.04.2017.
 * Форма добавления нового рецепта
 */
public class AddNewReceptTable implements Observer {



    private IngredientTableModel listTableModel;
    private SetIngredientsModel setIngredientsModel;
    private JTable setIngredientsTable;
    private JTable listTable;
    private JTextArea textRecept;

    @Override
    public void update(Observable o, Object arg) {
        setIngredientsModel.setListTable(Cash.getInstance().getIngredientsName());
        setIngredientsTable.updateUI();


    }
    void addRecept() {

        final JFrame frame = new JFrame("Создание нового рецепта");
        frame.setSize(new Dimension(600, 400));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        //Верхняя часть формы
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        final JLabel nameLabel = new JLabel("Название: ");
        final JTextField nameField = new JTextField();
        JLabel cookingTimeLabel = new JLabel("Время приготовления(в минутах)");

        final JTextField cookingTimeTextField = new JTextField();

        northPanel.add(nameLabel);
        northPanel.add(nameField);
        northPanel.add(cookingTimeLabel);
        northPanel.add(cookingTimeTextField);


        //Нижняя часть формы
        JButton addButton = new JButton("Создать");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean timeTrue = true;
                char[] f = cookingTimeTextField.getText().toCharArray();
                for (char aF : f) {
                    if (!Character.isDigit(aF))
                        timeTrue = false;

                }
                    if (!Cash.getInstance().goodDishName(nameField.getText()) || nameField.getText().contains("_") || !timeTrue)
                        JOptionPane.showMessageDialog(frame, "Блюдо с таким названием уже существует. Не допускается использование символа '_'. Время изготовления должно сотоять из цифр.");
                    else {

                        for (String[] s : listTableModel.getListTable()) {
                            char[] form = s[1].toCharArray();
                            StringBuilder sum = new StringBuilder();
                            for (char aForm : form)
                                if (Character.isDigit(aForm))
                                    sum.append(aForm);

                        }

                        Cash.getInstance().addDish(new Dish(nameField.getText(), listTableModel.getListTable(), Integer.parseInt(cookingTimeTextField.getText()), textRecept.getText()+"\n                    "));

                        frame.dispose();
                    }

                }

        });

        //Левая часть
        setIngredientsModel  = new SetIngredientsModel(Cash.getInstance().getIngredientsName());
        setIngredientsTable  = new JTable(setIngredientsModel);
        JButton addNewIngredient = new JButton("Добавить ингредиент");
        JScrollPane setIngredientsTableScrollPage = new JScrollPane(setIngredientsTable);
        setIngredientsTableScrollPage.setPreferredSize(new Dimension(400, 400));
        setIngredientsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = setIngredientsTable.rowAtPoint(e.getPoint());
                    String nameIngredient = (String) setIngredientsModel.getValueAt(row, 0);
                    setIngredientsModel.deleteString(row);
                    listTableModel.addString(nameIngredient);
                    setIngredientsTable.updateUI();
                    listTable.updateUI();
                }
            }
        });

        addNewIngredient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddNewIngredient addNewIngredient = new AddNewIngredient();
                addNewIngredient.addNewIngredient();

            }
        });
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(setIngredientsTableScrollPage);
        leftPanel.add(addNewIngredient);


        //Центральная часть
         listTableModel = new IngredientTableModel();
         listTable = new JTable(listTableModel);
        JScrollPane listTableScrollPage = new JScrollPane(listTable);
        listTableScrollPage.setPreferredSize(new Dimension(400, 400));
        listTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = listTable.rowAtPoint(e.getPoint());
                    String nameIngredient = (String) listTableModel.getValueAt(row, 0);
                    listTableModel.deleteDate(row);
                    setIngredientsModel.addString(nameIngredient);
                    setIngredientsTable.updateUI();
                    listTable.updateUI();
                }
            }
        });

        //Правая часть
            JLabel text = new JLabel("Описание приготовления");
            textRecept = new JTextArea(30, 50);
            JScrollPane textReceptScrollPane = new JScrollPane(textRecept);
            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            rightPanel.add(text);
            rightPanel.add(textReceptScrollPane);



        frame.getContentPane().add(BorderLayout.NORTH, northPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, addButton);
        frame.getContentPane().add(BorderLayout.WEST, leftPanel);

        frame.getContentPane().add(BorderLayout.CENTER, listTableScrollPage);
        frame.getContentPane().add(BorderLayout.EAST, rightPanel);

        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

    }




}
