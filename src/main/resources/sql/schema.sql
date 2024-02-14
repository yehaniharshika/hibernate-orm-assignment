create database libraryManagementSystem;
use libraryManagementSystem;

create table Author (
                        authorId int(20) primary key,
                        authorName varchar(200) not null
);

insert into Author (authorId,authorName)values
                                            (1, 'Kumarathuna Munidasa'),
                                            (2, 'Mahinda Thero'),
                                            (3, 'Sarath Perera'),
                                            (4, 'Lilananda Gamachchi'),
                                            (5,'Martin Wickramasingha');


create table Book (
                      bookId int(20) primary key ,
                      title varchar(100) not null ,
                      publicationYear int(20) not null,
                      price decimal not null ,
                      authorId int(20),
                      constraint foreign key(authorId) references Author(authorId) on update cascade on delete cascade
);

insert into Book (bookId, title, publicationYear,price,authorId) values
                                                                     (1, 'Hathpana',1990,550.00,1),
                                                                     (2, 'Waluka', 2001, 600.00,3),
                                                                     (3, 'Game lamai', 2002, 650.00,2),
                                                                     (4, 'Ape gama', 2015, 700.00,4),
                                                                     (5,'Iskole',1999,650.00,5);