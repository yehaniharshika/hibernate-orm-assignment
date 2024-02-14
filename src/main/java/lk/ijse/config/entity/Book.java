package lk.ijse.config.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity

public class Book {
    @Id
    private int bookId;
    private String title;
    private int publicationYear;
    private  double price;

    @ManyToOne
    private Author author;

    public Book(){

    }

    public Book(Author author, int bookId, double price, int publicationYear, String title) {
        this.author = author;
        this.bookId = bookId;
        this.price = price;
        this.publicationYear = publicationYear;
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
