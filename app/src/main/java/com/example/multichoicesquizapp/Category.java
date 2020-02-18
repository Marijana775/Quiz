package com.example.multichoicesquizapp;

public class Category {

    public static final int POVIJEST = 1;
    public static final int ZEMLJOPIS = 2;
    public static final int BIOLOGIJA = 3;
    public static final int RAČUNALA = 4;
    public static final int MATEMATIKA = 5;

    private int id;
    private String name;

    public  Category(){}

    public Category(String name) {
        this.name = name;
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
}
