package anhtuan.service;

import java.util.List;

public interface IService<T> {
    List<T> findAll();
    T findById(int id);
    boolean save(T p,int[]arr);
    boolean delete(int id);
    boolean edit(T t,int[]arr);
}
