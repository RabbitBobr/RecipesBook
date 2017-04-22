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

    public Ingredient(String name, Calories calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calories getCalories() {
        return calories;
    }

    public void setCalories(Calories calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return name + '_' + calories;
    }
}
