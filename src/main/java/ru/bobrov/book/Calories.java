package ru.bobrov.book;

/**
 * Created by Rabbik on 20.04.2017.
 * Класс предназначен для хранения модели БЖУ
 *
 */
public class Calories {
    private double calories;
    private double proteins;
    private double fats;
    private double carbohydrates;

    public Calories(double calories, double proteins, double fats, double carbohydrates) {
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public double getCalories() {
        return calories;
    }

    public double getProteins() {
        return proteins;
    }

    public double getFats() {
        return fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    @Override
    public String toString() {
        return calories +
                "_" + proteins +
                "_" + fats +
                "_" + carbohydrates + "\n";
    }
}
