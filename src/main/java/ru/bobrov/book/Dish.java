package ru.bobrov.book;


import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by Rabbik on 17.04.2017.
 * Класс предназначен для хранения объекта
 * содержащего данные о блюде
 */
public class Dish implements Serializable {
    private String name;
    private transient ArrayList<String[]> composition;
    private int cookingTime;
    private String instruction;

    public Dish() {

    }

    public Dish(String name, ArrayList<String[]> composition, int cookingTime, String instruction) {
        this.name = name;
        this.composition = composition;
        this.cookingTime = cookingTime;
        this.instruction = instruction;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String[]> getComposition() {
        return composition;
    }

    public void setComposition(ArrayList<String[]> composition) {
        this.composition = composition;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", composition=" + composition +
                ", cookingTime=" + cookingTime +
                ", instruction=" + instruction +
                '}';
    }
}
