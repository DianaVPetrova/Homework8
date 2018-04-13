package bg.softuni.diana.foodfacts.data;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM ProductItem")
    List<ProductItem> getAll();

    @Query("SELECT * FROM ProductItem WHERE uid IN (:productIds)")
    List<ProductItem> loadAllByIds(int[] productIds);

    @Query("SELECT * FROM ProductItem WHERE code LIKE :code LIMIT 1")
    ProductItem findByCode(String code);

    @Insert
    void insertAll(ProductItem... productItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertProducts(ProductItem... productItems);

    @Delete
    void delete(ProductItem user);
}
