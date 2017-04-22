package ru.bobrov.book.controllers;

import ru.bobrov.book.Calories;
import ru.bobrov.book.Dish;
import ru.bobrov.book.Ingredient;


import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Rabbik on 18.04.2017.
 *КЭШ проекта, при запуске приложения, данные считываются
 * При окончании работы, сохраняются на диск
 */
public class Cash extends Observable {
    private static Cash ourInstance = new Cash();
    private final String PATH_TO_RESOURCES = "classes/";
    private File fileIngredients = new File(PATH_TO_RESOURCES + "Ingredients.txt");
    private File fileData = new File(PATH_TO_RESOURCES + "data.txt");

    public static Cash getInstance() {
        return ourInstance;
    }
    private ArrayList<Ingredient> ingredients  = new ArrayList<>();
    private ArrayList<String []> listTable = new ArrayList<>();
    private ArrayList<Dish> setDish = new ArrayList<>();

    private Cash()  {
            String [] str;

                   try ( BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileIngredients), "UTF-8")))

                {
                String outString;

                    while ((outString = reader.readLine()) != null) {
                        str = outString.split("_");
                        ingredients.add(new Ingredient(str[0], Double.parseDouble(str[1]), Double.parseDouble(str[2]), Double.parseDouble(str[3]), Double.parseDouble(str[4])));

                    }


            } catch (IOException ex){
                ex.getMessage();
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileData), "UTF-8")))
            {
                String outString;
                while((outString = reader.readLine()) != null) {
                    str = outString.split("_");
                    String[] mass = {str[0], str[1], str[2]};
                    listTable.add(mass);

                }
            } catch (IOException ex) {
                ex.getMessage();
            }
        if (!listTable.isEmpty()) {
               for (String[] s : listTable) {
                   try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(PATH_TO_RESOURCES + s[1] + ".dish")))) {
                       Dish dish = (Dish) in.readObject();
                       int count = in.readInt();
                       ArrayList<String[]> listIngredients = new ArrayList<>();
                       for(int i=0; i<count; i++)
                           listIngredients.add((String[]) in.readObject());
                       dish.setComposition(listIngredients);
                       setDish.add(dish);
                   }  catch (IOException ex) {
                        ex.getMessage();
                   } catch (ClassNotFoundException e) {
                       e.printStackTrace();
                   }
               }
        }

        }

    public ArrayList<String> getIngredientsName() {
        ArrayList<String> result = new ArrayList<>();
        for(Ingredient i : ingredients)
            result.add(i.getName());
        return result;
    }

    public boolean goodDishName(String nameNewDish) {
        boolean result = true;
        for (Dish s : setDish)
            if (s.getName().equals(nameNewDish)) {
                result = false;
                break;
            }
            return result;
    }

    public boolean goodIngredientName(String nameNewIngredient) {
        boolean result = true;
        for (Ingredient i : ingredients) {
            if (i.getName().equals(nameNewIngredient)) {
                result = false;
                break;
            }
        }
        return result;
    }

    private Ingredient searchIngredient (String nameIngredient) {
        Ingredient result = null;
        for(Ingredient i : ingredients)
            if (nameIngredient.equals(i.getName()))
                result = i;
        return result;
    }

    public void addNewIngredient(String name, double cal, double prot, double fat, double carb) {
        ingredients.add(new Ingredient(name, cal, prot, fat, carb));
        setChanged();
        notifyObservers();
    }

    public ArrayList<String[]> getListTable() {
        return listTable;
    }

    public void addDish(Dish dish) {
        setDish.add(dish);

        String[] setData = { Integer.toString(listTable.size()+1), dish.getName(), Integer.toString(dish.getCookingTime())};
        listTable.add(setData);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(PATH_TO_RESOURCES + dish.getName() + ".dish")))) {
            out.writeObject(dish);
            out.writeInt(dish.getComposition().size());
            for(String[] s : dish.getComposition())
                out.writeObject(s);
        } catch (IOException ex) {
            ex.getMessage();
        }

        setChanged();
        notifyObservers();
    }

    public Calories countCalories(ArrayList<String[]> ingredients) {
        double calories = 0;
        double proteins = 0;
        double fats = 0;
        double carbohydrates = 0;

        for (String[] s : ingredients) {
            try {
                double massaIngredient = Double.parseDouble(s[1]);


            Ingredient ingredient = searchIngredient(s[0]);
            calories+=((ingredient.getCalories().getCalories()/100) * massaIngredient);
            proteins+=((ingredient.getCalories().getProteins()/100) * massaIngredient);
            fats+=((ingredient.getCalories().getFats()/100) * massaIngredient);
            carbohydrates+=((ingredient.getCalories().getCarbohydrates()/100)* massaIngredient);
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
            }
        }

        return new Calories(calories, proteins, fats, carbohydrates);
    }

    public Dish searchDish(String dishName) {
        Dish result = null;
        for (Dish d : setDish) {
            if (d.getName().equals(dishName)) {
                result = d;
                break;
            }
        }
        return result;
    }

    public void updateDish(Dish dish) {
        ArrayList<Dish> copy = new ArrayList<>(setDish);
        for (int i=0; i<copy.size(); i++) {
            if(copy.get(i).getName().equals(dish.getName())) {
                setDish.remove(i);
                    ArrayList<String[]> copyListTable = new ArrayList<>(listTable);
                    for (String[] s : copyListTable) {
                        if (s[1].equals(dish.getName())) {
                            listTable.remove(s);
                            break;
                        }
                    }
                addDish(dish);
                break;
            }
        }
    }

    public String[] arrayToMasIngredients() {
        String[] result = new String[ingredients.size()];
        for(int i=0; i<ingredients.size(); i++) {
            result[i] = ingredients.get(i).getName();
        }
        return result;
    }

    public void saveData() {

        try(BufferedWriter writeData = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileData), "UTF-8"));
            BufferedWriter writeIngredients = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileIngredients), "UTF-8"))) {
            for (String[] s : listTable) {
                writeData.write(Integer.parseInt(s[0])+"_"+s[1]+"_"+s[2]+"\n");
            }
            for (Ingredient s : ingredients) {
                writeIngredients.write(s.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
