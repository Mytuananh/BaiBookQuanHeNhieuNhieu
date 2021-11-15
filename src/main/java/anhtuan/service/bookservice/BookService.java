package anhtuan.service.bookservice;

import anhtuan.config.ConnectionSingleton;
import anhtuan.model.Book;
import anhtuan.model.Category;
import anhtuan.service.categoryservice.CategoryService;
import anhtuan.service.categoryservice.ICategoryService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookService implements IBookService{
    private final Connection connection = ConnectionSingleton.getConnection();
    private final CategoryService categoryService = new CategoryService();

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int bId = rs.getInt("bId");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                List<Category> categories = categoryService.findAllCategoryForOneBook(bId);
                Book book = new Book(bId, name, price, description,categories);
                bookList.add(book);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return bookList;
    }

    @Override
    public Book findById(int bId) {
        List<Category> categories = new ArrayList<>();
        Book book = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select  * from book where bId = ?");
            preparedStatement.setInt(1, bId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                book = new Book(bId, name, price, description);
            }
            PreparedStatement preparedStatement1 = connection.prepareStatement("select * from book_category where  bId = ?");
            preparedStatement1.setInt(1,bId);
            ResultSet rs1 = preparedStatement1.executeQuery();
            while (rs1.next()) {
                int cId  = rs1.getInt("cId");
                Category category = categoryService.findById(cId);
                categories.add(category);
            }
            book.setCategories(categories);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return book;
    }

    @Override
    public boolean save(Book p, int[] arr) {
        boolean rowSave = false;
        int bId = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into book(name,price,description) value (?,?,?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,p.getName());
            preparedStatement.setInt(2,p.getPrice());
            preparedStatement.setString(3,p.getDescription());
            rowSave = preparedStatement.executeUpdate() > 0;

            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                bId = rs.getInt(1);
            }
            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into book_category value (?, ?)");
            for (int a: arr) {
                preparedStatement1.setInt(1,bId);
                preparedStatement1.setInt(2, a);
                preparedStatement1.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
            return rowSave;
    }

    public List<Book> findByName(String name) {
        List<Book> bookList = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        Book book;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book where name = ?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int bId = rs.getInt("bId");
                int price = rs.getInt("price");
                String description = rs.getString("description");
                categories = categoryService.findAllCategoryForOneBook(bId);
                book = new Book(bId, name, price, description, categories);
                bookList.add(book);
            }
//            PreparedStatement preparedStatement1 = connection.prepareStatement("select * from book_category where bId = ?");
//            preparedStatement1.setInt(1, book.getId());
//            ResultSet rs1 = preparedStatement1.executeQuery();
//            while (rs1.next()) {
//                int cId = rs1.getInt("cId");
//                Category category = categoryService.findById(cId);
//                categories.add(category);
//            }
//            book.setCategories(categories);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bookList;
    }

    @Override
    public boolean delete(int bId) {
        boolean rowDelete = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from book where bId = ?");
            preparedStatement.setInt(1,bId);
            rowDelete = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
            return rowDelete;
    }

    @Override
    public boolean edit(Book book, int[] arr) {
        boolean rowEdit = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update book set name = ?, price = ?, description = ? where bId = ?");
            preparedStatement.setString(1, book.getName());
            preparedStatement.setInt(2, book.getPrice());
            preparedStatement.setString(3, book.getDescription());
            preparedStatement.setInt(4,book.getId());
            rowEdit = preparedStatement.executeUpdate() > 0;

            PreparedStatement preparedStatement1 = connection.prepareStatement("delete from book_category where bId = ?");
            for (int a: arr) {
                preparedStatement1.setInt(1, book.getId());
                preparedStatement1.executeUpdate();
            }

            PreparedStatement preparedStatement2 = connection.prepareStatement("insert into book_category value (?, ?)");
            for (int a: arr) {
                preparedStatement2.setInt(1,book.getId());
                preparedStatement2.setInt(2,a);
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowEdit;
    }
}
