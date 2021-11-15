package anhtuan.controller;

import anhtuan.model.Book;
import anhtuan.model.Category;
import anhtuan.service.bookservice.BookService;
import anhtuan.service.bookservice.IBookService;
import anhtuan.service.categoryservice.CategoryService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/Book")
public class BookServlet extends HttpServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null){
            action ="";
        }
        switch (action){
            case "create":
                showCreateForm(request,response);
                break;
            case "edit":
                showEditForm(request,response);
                break;
            case "delete":
                deleteBook(request,response);
                break;
            default:
                listBooks(request,response);
                break;
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) {
        int bId = Integer.parseInt(request.getParameter("id"));
        try {
            bookService.delete(bId);
            response.sendRedirect("/Book");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) {
        int bId = Integer.parseInt(request.getParameter("id"));
        Book book = bookService.findById(bId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("books/edit.jsp");
        request.setAttribute("book", book);
        request.setAttribute("categories",categoryService.findAll());
        try {
            dispatcher.forward(request,response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response) {
        List<Book> bookList;
        String name = request.getParameter("name");
        if (name != null && name != "") {
            bookList = bookService.findByName(name);
        } else {
            bookList = bookService.findAll();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("books/list.jsp");
        request.setAttribute("bookList", bookList);
        try {
            dispatcher.forward(request,response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categories = categoryService.findAll();
        RequestDispatcher dispatcher = request.getRequestDispatcher("books/create.jsp");
        request.setAttribute("categories", categories);
        try {
            dispatcher.forward(request,response);
    } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null){
            action ="";
        }
        switch (action){
            case "create":
                createBook(request,response);
                break;
            case "edit":
                updateBook(request,response);
                break;
        }
    }

    private void createBook(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        String description = request.getParameter("description");
        String[] categories = request.getParameterValues("categories");
        int[] cId = new int[categories.length];
        for (int i = 0; i < categories.length; i++) {
            cId[i] = Integer.parseInt(categories[i]);
        }
        Book book = new Book(name, price, description);
        bookService.save(book, cId);
        request.setAttribute("message","Sach da duoc them vao!");
        request.setAttribute("categories",categoryService.findAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("books/create.jsp");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categoryList = new ArrayList<>();
        int bId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        String description = request.getParameter("description");
        String[] categories = request.getParameterValues("categories");
        int[] cId = new int[categories.length];
        for (int i = 0; i < categories.length; i++) {
            cId[i] = Integer.parseInt(categories[i]);
        }
        for (int c: cId) {
            Category category = categoryService.findById(c);
            categoryList.add(category);
        }
        Book book = new Book(bId, name, price, description, categoryList);
        request.setAttribute("chooseList", categoryService.findAll());
        request.setAttribute("message","Sach da duoc cap nhat!");
        request.setAttribute("book", book);
        RequestDispatcher dispatcher = request.getRequestDispatcher("books/edit.jsp");
        try {
            bookService.edit(book,cId);
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

    }
}
