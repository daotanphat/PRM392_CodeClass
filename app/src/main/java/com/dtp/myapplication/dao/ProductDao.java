package com.dtp.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.dtp.myapplication.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAllProducts();

    @Query("SELECT * FROM product WHERE product_id = :id")
    Product selectById(int id);

    @Query("DELETE FROM product WHERE product_id = :id")
    void deleteById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Product> products);

    @Query("DELETE FROM product")
    void deleteAll();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Product product);
}
