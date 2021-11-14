package anhtuan.service.categoryservice;

import anhtuan.config.ConnectionSingleton;
import anhtuan.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService implements ICategoryService{
    Connection connection = ConnectionSingleton.getConnection();
    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from category");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int cId = rs.getInt("cId");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Category category = new Category(cId, name, description);
                categories.add(category);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return categories;
    }

    @Override
    public Category findById(int cId) {
        Category category = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from category where cId = ?");
            preparedStatement.setInt(1,cId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                category = new Category(cId, name, description);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return category;
    }

    @Override
    public boolean save(Category p, int[] arr) {
        boolean rowSave = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into category (name, description) value (?, ?)");
            preparedStatement.setString(1, p.getName());
            preparedStatement.setString(2, p.getDescription());
            rowSave = preparedStatement.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowSave;
    }

    @Override
    public boolean delete(int cId) {
        boolean rowDelete = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete category where cId = ?");
            preparedStatement.setInt(1, cId);
            rowDelete = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowDelete;
    }

    @Override
    public boolean edit(Category category, int[] arr) {
        boolean rowUpdate = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update category set name = ?, description = ? where cId = ?");
            preparedStatement.setString(1,category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3,category.getId());
            rowUpdate = preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowUpdate;
    }
    public List<Category> findAllCategoryForOneBook(int bId) {
        List<Category> categories = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from book_category  where bId = ?");
            preparedStatement.setInt(1,bId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int cId = rs.getInt("cId");
                Category category = findById(cId);
                categories.add(category);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return categories;
    }
}
