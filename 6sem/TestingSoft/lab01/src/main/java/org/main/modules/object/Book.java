package org.main.modules.object;

public class Book {
    String title;
    String author;
    int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getSummary() {
        return title + " by " + author + ", published in " + year;
    }

    public boolean isClassic() {
        return year < 2000;
    }
}