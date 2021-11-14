package anhtuan.model;

import java.util.List;

public class Book {
    private int id;
    private String name;
    private int price;
    private String description;
    private List<Category> categories;

    public Book(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Book(String name, int price, String description, List<Category> categories) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Book(int id, String name, int price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Book(int id, String name, int price, String description, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


