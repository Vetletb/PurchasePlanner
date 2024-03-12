package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;
import java.util.List;
public class Recipe implements Storable {
    private int recipe_id;
    private String name;
    private String category;
    private int cooking_time;

    public Recipe(String name, String category, int cooking_time) {
        this.name = name;
        this.cooking_time = cooking_time;
        this.category = category;
    }

    public Recipe(int recipe_id, String name, String category, int cooking_time) {
        this(name, category, cooking_time);
        this.recipe_id = recipe_id;
    }

    @Override
    public List<String> getAttributes() {
        List<String> attributes = new ArrayList<>();
        attributes.add(name);
        attributes.add(Integer.toString(cooking_time));
        attributes.add(category);
        return attributes;
    }

    @Override
    public List<String> getAttributeNames() {
        List<String> attributes = new ArrayList<>();
        attributes.add("name");
        attributes.add("cooking_time");
        attributes.add("category");
        return attributes;
    }

    @Override
    public int getId() {
        return recipe_id;
    }

    @Override
    public String getIdName() {
        return "recipe_id";
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getCooking_time() {
        return cooking_time;
    }
}
