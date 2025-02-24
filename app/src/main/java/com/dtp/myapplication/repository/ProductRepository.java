package com.dtp.myapplication.repository;

import android.content.Context;

import com.dtp.myapplication.dao.ProductDao;
import com.dtp.myapplication.dao.ProductRoomDatabase;
import com.dtp.myapplication.entity.Product;

import java.util.List;

public class ProductRepository {
    private ProductDao productDao;

    public ProductRepository(Context context) {
        ProductRoomDatabase productRoomDatabase = ProductRoomDatabase.getInstance(context);
        productDao = productRoomDatabase.getProductDao();
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public Product selectById(int id) {
        return productDao.selectById(id);
    }

    public void deleteById(int id) {
        productDao.deleteById(id);
    }

    public void insert(Product product) {
        productDao.insert(product);
    }

    public void insertAll(List<Product> products) {
        productDao.insertAll(products);
    }

    public void deleteAll() {
        productDao.deleteAll();
    }

    public void update(Product product) {
        productDao.update(product);
    }
}
