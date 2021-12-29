package com.example.demo;

import com.example.demo.controller.utils.AESEncryption;
import com.example.demo.controller.utils.BookDataReader;
import com.example.demo.models.Book;
import com.example.demo.models.Review;
import com.example.demo.models.User;
import com.example.demo.repositories.ReviewRepository;
import com.example.demo.services.BookService;
import com.example.demo.services.OrderService;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BooklineApplicationTests {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookDataReader bookDataReader;

    @Test
    void contextLoads() {
    }

    @Test
    public void getAllBooksTest(){
        System.out.println(bookService.getAllBooks());
    }

    @Test
    public void deleteBook(){
        bookService.deleteAll();
    }

    @Test
    public void addBooksToElasticTest(){

        Book book1 = Book.builder()
                .bookTitle("The Four Loves")
                .bookAuthor("Clive Staples Lewis")
                .bookCategory("Christian life")
                .bookDescription("Lewis' work on the nature of love divides love into four categories; Affection, Friendship, Eros and Charity. The first three come naturally to humanity. Charity, however, the Gift-love of God, is divine, and without this supernatural love, the natural loves become distorted and even dangerous.")
                .bookImage("http://books.google.com/books/content?id=XhQ5XsFcpGIC&printsec=frontcover&img=1&zoom=1&source=gbs_api")
                .bookIsbn("9780006280897")
                .bookPages("170")
                .bookPrice(200)
                .bookRating(4.15)
                .yearPublished("2002")
                .build();

        Book book2 = Book.builder()
                .bookTitle("Master of the Game")
                .bookAuthor("Sidney Sheldon")
                .bookCategory("Adventure stories")
                .bookDescription("Kate Blackwell is an enigma and one of the most powerful women in the world. But at her ninetieth birthday celebrations there are ghosts of absent friends and absent enemies.    ")
                .bookImage("http://books.google.com/books/content?id=TkTYp-Tp6_IC&printsec=frontcover&img=1&zoom=1&source=gbs_api")
                .bookIsbn("9780006472612")
                .bookPages("489")
                .bookPrice(250)
                .bookRating(4.11)
                .yearPublished("1982")
                .build();

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        bookService.createBookIndexBulk(books);

        System.out.println("##" + bookService.findBook("Book"));
    }

    @Test
    public void addBook(){
        Book book = Book.builder()
                .bookTitle("Letters to Children")
                .yearPublished("1985")
                .bookRating(4.18)
                .bookPrice(400)
                .bookPages("128")
                .bookIsbn("9780805420432")
                .bookImage("http://books.google.com/books/content?id=jfRfxAEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api")
                .bookDescription("A collection of letters from the English author of the Narnia books to a variety of children.")
                .bookCategory("Religion")
                .bookAuthor("Clive Staples Lewis")
                .build();

        List<Book> books = new ArrayList<>();
        books.add(book);
        bookService.createBookIndexBulk(books);
    }

    @Test
    public void updateBookTest(){
        bookService.updateBook(bookService.findBook("The Four Loves").get(0), "bookPrice", "5000");
    }

    @Test
    public void displayUsers(){
        for(User u: userService.findAllUsers()){
            System.out.println(u);
        }
    }

    @Test
    public void addUser(){
        LocalDate date = LocalDate.now();
        User user = User.builder()
                .userName("John")
                .userEmail("john@email.com")
                .password("John123")
                .userRole("user")
                .userDateOfBirth(date)
                .isAuthenticated(false)
                .build();
        userService.createUserIndex(user);
        displayUsers();
    }

    @Test
    public void deleteUsers() {
        userService.deleteUser("yJlA_H0Bxi_wX5zUkChN");
        displayUsers();
    }

    @Test
    public void encryptionTest(){
        String password = "Max123_00";
        String key = "secret_key";
        AESEncryption aesEncryption = new AESEncryption();
        String encoded = aesEncryption.encrypt(password, key);
        System.out.println(encoded);
        System.out.println(aesEncryption.decrypt(encoded, key));
    }

    @Test
    public void addBookToWishlistTest(){
        Book book = bookService.getAllBooks().get(0);
        User user = userService.findAllUsers().get(0);
        userService.addBookToWishlist(user, book.getBookTitle());
    }

    @Test
    public void addBookToCartTest(){
        User user = userService.findAllUsers().get(0);
        userService.addBookToCart(user, bookService.getAllBooks().get(0).getBookTitle());
        userService.addBookToCart(user, bookService.getAllBooks().get(1).getBookTitle());
        displayUsers();
    }

    @Test
    public void getAllOrders(){
        System.out.println(orderService.getAllOrders());
    }

    @Test
    public void deleteAllOrders(){
        orderService.deleteAllOrders();
        getAllOrders();
    }

    @Test
    public void getAllReviews(){
        for(Review review: reviewRepository.findAll()){
            System.out.println(review);
        }
    }

    @Test
    public void saveReview(){
        Review review = Review.builder()
                .bookId("1")
                .reviewDate(LocalDate.now())
                .build();
        reviewRepository.save(review);
        getAllReviews();
    }

    @Test
    public void deleteReview(){
        reviewRepository.deleteAll();
    }

    @Test
    public void readFIle(){
        bookDataReader.readData();
    }
}
