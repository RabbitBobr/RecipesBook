package ru.bobrov.book.gui;

import ru.bobrov.book.Calories;
import ru.bobrov.book.Dish;
import ru.bobrov.book.controllers.Cash;
import ru.bobrov.book.model.IngredientTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Rabbik on 18.04.2017.
 * Форма просмотра и редактирования рецептов
 */
class RecipesTable {

    private JTextField timeTextField;
    private IngredientTableModel ingredientTableModel;
    private JTextArea textAreaRecept;
    private JFrame frame;


     void seeRecept(final Dish dish) {
        frame = new JFrame("Блюдо: " + dish.getName());
        frame.setSize(new Dimension(1000, 400));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        final JPanel framePanel = new JPanel();
        framePanel.setLayout(new BorderLayout());
        frame.getContentPane().add(framePanel);

        //Первая панель - время приготовления
        JLabel timeLabel = new JLabel("Время приготовления");
        timeTextField = new JTextField();
        timeTextField.setText(Integer.toString(dish.getCookingTime()));
        final JPanel timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
        timePanel.add(timeLabel);
        timePanel.add(timeTextField);

        //Вторая панель - ингредиенты и их количество
         ingredientTableModel = new IngredientTableModel();
        ingredientTableModel.setListTable(dish.getComposition());
        JTable tableIngredients = new JTable(ingredientTableModel);
        final JScrollPane ingredientsTableScrollPane = new JScrollPane(tableIngredients);
        ingredientsTableScrollPane.setSize(300, 100);

        //Третья панель: БЖУ и калории
        final JPanel caloriesPanel = new JPanel();
        caloriesPanel.setLayout(new BoxLayout(caloriesPanel, BoxLayout.Y_AXIS));
        int massa = ingredientTableModel.MassaIngredients();
        JLabel massLabel = new JLabel("Масса блюда составит: " + massa);
        JLabel infoLabel = new JLabel("Калорийность и БЖУ на 100г составит: ");
        Calories calories = Cash.getInstance().countCalories(ingredientTableModel.getListTable());
        JLabel calLabel = new JLabel(String.format("Ккал: %.2f", (calories.getCalories()/massa)*100));
        JLabel proLabel = new JLabel(String.format("Белки: %.2f", (calories.getProteins()/massa)*100));
        JLabel fatLabel = new JLabel(String.format("Жиры: %.2f" , (calories.getFats()/massa)*100));
        JLabel carLabel = new JLabel(String.format("Углеводы: %.2f" , (calories.getCarbohydrates()/massa)*100));
        caloriesPanel.add(massLabel);
        caloriesPanel.add(infoLabel);
        caloriesPanel.add(calLabel);
        caloriesPanel.add(proLabel);
        caloriesPanel.add(fatLabel);
        caloriesPanel.add(carLabel);

        //Четвертая панель - описание процесса приготовления
        textAreaRecept = new JTextArea(dish.getInstruction());

        final JScrollPane textAreaReceptScrollPane = new JScrollPane(textAreaRecept);
        textAreaReceptScrollPane.setSize(30, 40);

        //Редактирование
        final JButton refreshButton = new JButton("Обновить данные");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dish.setCookingTime(Integer.parseInt(timeTextField.getText()));
                    dish.setComposition(ingredientTableModel.getListTable());
                    dish.setInstruction(textAreaRecept.getText());
                    frame.dispose();
                    seeRecept(dish);
                    Cash.getInstance().updateDish(dish);
                } catch(Exception ex) {
                    ex.getMessage();
                }
            }
        });


        framePanel.add(BorderLayout.NORTH, timePanel);
            framePanel.add(BorderLayout.EAST, ingredientsTableScrollPane);
        framePanel.add(BorderLayout.WEST, caloriesPanel);
            framePanel.add(BorderLayout.CENTER, textAreaReceptScrollPane);
        framePanel.add(BorderLayout.SOUTH, refreshButton);

        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);


    }
}
