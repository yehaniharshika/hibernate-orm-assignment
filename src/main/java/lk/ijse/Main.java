package lk.ijse;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.config.entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class Main {
    public static boolean deleteAuthor(int id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query deleteBooksQuery = session.createQuery("delete from Book b where b.author.id = :authorId");
        deleteBooksQuery.setParameter("authorId", id);
        deleteBooksQuery.executeUpdate();

        Query deleteAuthorQuery = session.createQuery("delete from Author a where a.id = :authorId");
        deleteAuthorQuery.setParameter("authorId", id);
        boolean isDeleted = deleteAuthorQuery.executeUpdate() > 0;
        System.out.println(isDeleted);
        if (isDeleted){
            System.out.println("deleted success");
        }
        transaction.commit();
        session.close();
        return isDeleted;
    }


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
        System.out.println("---------------------------------------------------------------------------------------------");

        /*2. Write an HQL update query to increase the price of all books published by a specific
        author by 10%.*/
        Query updateQuery = session.createQuery("update Book  set price = price * 1.1 where id = :authorId");
        updateQuery.setParameter("authorId", 1); //author id
        int numOfBooksUpdated = updateQuery.executeUpdate();
        System.out.println("Number of books updated: " + numOfBooksUpdated);

        //to print Book's updated price
        Query fetchQuery = session.createQuery("select b from Book b where b.id = :bookId");
        fetchQuery.setParameter("bookId", 2); //specific bookId to fetch
        Book book = (Book) fetchQuery.uniqueResult();

        System.out.println("Updated price of book " + book.getBookId() + ": " + book.getPrice());
        System.out.println("---------------------------------------------------------------------------------------------");


        /*3. Implement a method to delete an author and cascade the deletion to all associated books
        using appropriate cascade options.*/
        deleteAuthor(book.getBookId());
        System.out.println("---------------------------------------------------------------------------------------------");


        /*4. Write an HQL query to find the average price of all books.*/
        Query avqQuery =  session.createQuery("select avg(b.price) from Book b ");
        double avgPrice = (double) avqQuery.uniqueResult();
        System.out.println(avgPrice);
        System.out.println("---------------------------------------------------------------------------------------------");


        /*5. Write an HQL query to retrieve all authors along with the count of books they have written.*/
        String hql = "select a.authorId, a.authorName, count (b.bookId) from Author a join Book b on a.authorId=b.author.authorId group by a.authorId, a.authorName";
        Query listQuery = session.createQuery(hql);
        System.out.println(listQuery);
        List<Object[]> list = listQuery.getResultList();
        for (Object[] object : list){
            int authorId = (int) object[0];
            String authorName = (String) object[1];
            long bookCount = (long) object[2];

            System.out.println(authorId + " , " + authorName + " , " + bookCount);
        }
        System.out.println("---------------------------------------------------------------------------------------------");


        /*6. Write an HQL query using named parameters to retrieve books written by authors from a
        specific country.*/
        Query query = session.createQuery("select b from Author a join Book b on a.authorId =b.author.authorId where a.country = :country");
        query.setParameter("country","Japan");
        List<Book> books = query.getResultList();
        for (Book book1 : books){
            int bookId = book1.getBookId();
            String title = book1.getTitle();
            int publicationYear = book1.getPublicationYear();
            double price =  book1.getPrice();
            int authorId = book1.getAuthor().getAuthorId();

            System.out.println(bookId+" , "+ title +" , "+publicationYear+" , "+price+" , "+authorId);
        }
        System.out.println("---------------------------------------------------------------------------------------------");

        /*7. Define bidirectional one-to-many relationship between Author and Book entities using
        @JoinColumn annotation*/

        /*10. Write an HQL query to find authors who have written more than the average number of
        books.*/



        transaction.commit();
        session.close();
    }

}