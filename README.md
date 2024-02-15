HQL Query Assignment

1. Write an HQL query to retrieve all books published after the year 2010.

   String hql1 ="from Book where publicationYear > 2010 ";
   List<Book> bookList = session.createQuery(hql1, Book.class).getResultList();

        for (Book book : bookList){
            System.out.println(book.getTitle());
            System.out.println(book.getPublicationYear());
            System.out.println(book.getPrice());
        }


2. Write an HQL update query to increase the price of all books published by a specific
   author by 10%.

   Query updateQuery = session.createQuery("update Book b set b.price = b.price * 1.1 where b.author.authorId = :authorId");
   updateQuery.setParameter("authorId", 4); //author id
   int numOfBooksUpdated = updateQuery.executeUpdate();
   System.out.println("Number of books updated: " + numOfBooksUpdated);

        //to print Book's updated price
        Query fetchQuery = session.createQuery("select b from Book b where b.id = :bookId");
        fetchQuery.setParameter("bookId", 3); //specific bookId to fetch
        Book book = (Book) fetchQuery.uniqueResult();

        System.out.println("Updated price of book " + book.getBookId() + ": " + book.getPrice());


3. Implement a method to delete an author and cascade the deletion to all associated books
   using appropriate cascade options.

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


4. Write an HQL query to find the average price of all books.

   Query avqQuery =  session.createQuery("select avg(b.price) from Book b ");
   double avgPrice = (double) avqQuery.uniqueResult();
   System.out.println(avgPrice);

5. Write an HQL query to retrieve all authors along with the count of books they have written.

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

6. Write an HQL query using named parameters to retrieve books written by authors from a
   specific country.

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

7. Define bidirectional one-to-many relationship between Author and Book entities using
   @JoinColumn annotation.

   @ManyToOne
   @JoinColumn(name = "authorId")
   private Author author;

10. Write an HQL query to find authors who have written more than the average number of
    books.

    Query Lastquery = session.createQuery("select a.authorName from Book b join Author a on b.author.authorId = a.authorId group by " +
    "b.author.authorId having count(b.bookId) > (select avg(sub.avgBookCount) from (select count(b1.bookId) as avgBookCount from " +
    "Book b1 group by b1.author.authorId) as sub)");
    List<String> nameList = Lastquery.list();

        for (String name : nameList) {
            System.out.println(name);
        }


