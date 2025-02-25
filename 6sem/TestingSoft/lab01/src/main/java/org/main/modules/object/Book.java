package org.main.modules.object;

public class Book {
    String title;
    int year;
    Author author = null;

    public Book(String title, int year, Author author) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getSummary() {
        return title + " by " + this.author.getName() + ", published in " + year;
    }

    public boolean isClassic() {
        return year < 2000;
    }

    public boolean isVariableYear(){
        if(this.year <= 0){
            throw new IllegalArgumentException("Year must be greater than zero");
        }
        else {
            return true;
        }
    }
}