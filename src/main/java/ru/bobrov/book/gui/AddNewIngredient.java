package ru.bobrov.book.gui;

import ru.bobrov.book.controllers.Cash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Rabbik on 22.04.2017.
 * Форма добавления нового ингредиента
 */
public class AddNewIngredient extends JFrame {

    void addNewIngredient() {
        final JFrame frame = new JFrame("Создание нового ингредиента");
        frame.setSize(new Dimension(600, 400));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        JLabel nameLabel = new JLabel("Название: ");
        JLabel caloriesLabel = new JLabel("Кол-во Ккал: ");
        JLabel protLabel = new JLabel("Белки: ");
        JLabel fatLabel = new JLabel("Жиры: ");
        JLabel carbLabel = new JLabel("Углеводы: ");

        final JTextArea nameTextArea = new JTextArea();
        final JTextArea calTextArea = new JTextArea();
        final JTextArea protTextArea = new JTextArea();
        final JTextArea fatTextArea = new JTextArea();
        final JTextArea carbTextArea = new JTextArea();

        JButton addIngredientButton = new JButton("Сохранить");
        JButton delButton = new JButton("Очистить поля");
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameTextArea.setText("");
                calTextArea.setText("");
                protTextArea.setText("");
                fatTextArea.setText("");
                carbTextArea.setText("");

            }
        });

        addIngredientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (nameTextArea.getText()==null || nameTextArea.getText().equals("") || !Cash.getInstance().goodIngredientName(nameTextArea.getText()) || nameTextArea.getText().contains("_"))
                    JOptionPane.showMessageDialog(frame, "Данное имя уже используется или не введено. Запрещается использовать символ '_'");
                else {
                    try {
                        Cash.getInstance().addNewIngredient(nameTextArea.getText(), Double.parseDouble(calTextArea.getText()),
                                Double.parseDouble(protTextArea.getText()), Double.parseDouble(fatTextArea.getText()),
                                Double.parseDouble(carbTextArea.getText()));
                        JOptionPane.showMessageDialog(frame, "Добален новый ингредиент");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Проверьте введенные числа. Дробная часть отделяется символом '.'");
                    }

                }

            }
        });
//Название
        frame.add(nameLabel, new GridBagConstraints(0,1,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1), 0, 0));
        frame.add(nameTextArea, new GridBagConstraints(1,1,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1), 0, 0));
//Ккал
        frame.add(caloriesLabel, new GridBagConstraints(0,2,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1), 0, 0));
        frame.add(calTextArea, new GridBagConstraints(1,2,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1), 0, 0));
//Бели
        frame.add(protLabel, new GridBagConstraints(0,3,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1), 0, 0));
        frame.add(protTextArea, new GridBagConstraints(1,3,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1), 0, 0));
//Жиры
        frame.add(fatLabel, new GridBagConstraints(0,4,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1), 0, 0));
        frame.add(fatTextArea, new GridBagConstraints(1,4,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1), 0, 0));
//Углеводы
        frame.add(carbLabel, new GridBagConstraints(0,5,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1), 0, 0));
        frame.add(carbTextArea, new GridBagConstraints(1,5,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1), 0, 0));
//Кнопки
        frame.add(addIngredientButton, new GridBagConstraints(0,6,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1), 0, 0));
        frame.add(delButton, new GridBagConstraints(1,6,1,1,1,1,
                GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,
                new Insets(1,1,1,1), 0, 0));

        frame.setVisible(true);
        frame.pack();
    }
}
