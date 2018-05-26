package sample;

import javafx.beans.property.SimpleStringProperty;

public class Book {
    private int id;
    private String title;
    private String author;
    private int year;

    public Book(int id,String title,String author,int year){
        this.id=id;
        this.title=title;
        this.author=author;
        this.year=year;
    }

    public void setId(int id) {
        this.id= id;
       // this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = new String(title);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author =new String( author);
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }
}
