package ru.bobrov.book;


/**
 * Класс представляет собой модель ингредиента
 */
public class Ingredient {
    private String name;
    private Calories calories;

    public Ingredient(String name, double calories, double proteins, double fats, double carbohydrates) {
        this.name = name;
        this.calories = new Calories(calories, proteins, fats, carbohydrates);

    }

    public String getName() {
        return name;
    }

    public Calories getCalories() {
        return calories;
    }

    @Override
    public String toString() {
        return name + '_' + calories;
    }
}
