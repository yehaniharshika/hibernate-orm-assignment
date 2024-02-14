package lk.ijse;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.config.entity.Author;
import lk.ijse.config.entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class Main {
    public static void delete(Author author) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        // Delete the associated books first (cascading deletion)
        for (Book book : author.getBookList()) {
            session.delete(book);
        }

        // Now delete the author
        session.delete(author);

        transaction.commit();
        session.close();
    }

    /*public static void delete(){
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete from Author");
        int isDeleted = query.executeUpdate();
        System.out.println(isDeleted);

        transaction.commit();
        session.close();
    }*/

    public static void main(String[] args) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        /*1. Write an HQL query to retrieve all books published after the year 2010.*/
        String hql1 ="from Book where publicationYear > 2010 ";
        List<Book> bookList = session.createQuery(hql1, Book.class).getResultList();

        for (Book book : bookList){
            System.out.println(book.getTitle());
            System.out.println(book.getPublicationYear());
            System.out.println(book.getPrice());
        }

        /*2. Write an HQL update query to increase the price of all books published by a specific
        author by 10%.*/
        Query query = session.createQuery("update Book set price = price * 1.1 where author.authorId = :authorId");
        query.setParameter("authorId", 4); //author id
        int numOfBooksUpdated = query.executeUpdate();
        System.out.println("Number of books updated: " + numOfBooksUpdated);

        //to print Book's updated price
        Query fetchQuery = session.createQuery("select b from Book b where b.id = :bookId");
        fetchQuery.setParameter("bookId", 6); //specific bookId to fetch
        Book book = (Book) fetchQuery.uniqueResult();

        System.out.println("Updated price of book " + book.getBookId() + ": " + book.getPrice());

        /*3. Implement a method to delete an author and cascade the deletion to all associated books
        using appropriate cascade options.*/

//        Author author = new Author();
//        if (author != null){
//            delete(author); // Call the delete method with the retrieved author
//            System.out.println("Author and associated books deleted");
//        }

        /*4. Write an HQL query to find the average price of all books.*/
        Query avqQuery =  session.createQuery("select avg(b.price) from Book b ");
        double avgPrice = (double) avqQuery.uniqueResult();
        System.out.println(avgPrice);



        /*5. Write an HQL query to retrieve all authors along with the count of books they have written.*/
        transaction.commit();
        session.close();
    }

}