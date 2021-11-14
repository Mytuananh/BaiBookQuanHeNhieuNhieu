package anhtuan.service.categoryservice;

import anhtuan.model.Category;
import anhtuan.service.IService;

import java.util.List;

public interface ICategoryService extends IService<Category> {
    @Override
    List<Category> findAll();

    @Override
    Category findById(int id);

    @Override
    boolean save(Category p, int[] arr);

    @Override
    boolean delete(int id);

    @Override
    boolean edit(Category category, int[] arr);
}
