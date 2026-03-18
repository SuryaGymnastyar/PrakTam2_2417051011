package uts;

import java.util.ArrayList;

public class Book {
    private ArrayList<String> name;
    private ArrayList<String> author;
    private ArrayList<Double> price;
    private ArrayList<Integer> stock;

    public Book(ArrayList<String> name, ArrayList<String> author, ArrayList<Double> price, ArrayList<Integer> stock) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public ArrayList<String> getAuthor() {
        return author;
    }

    public void setAuthor(ArrayList<String> author) {
        this.author = author;
    }

    public ArrayList<Double> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Double> price) {
        this.price = price;
    }

    public ArrayList<Integer> getStock() {
        return stock;
    }

    public void setStock(ArrayList<Integer> stock) {
        this.stock = stock;
    }
}
