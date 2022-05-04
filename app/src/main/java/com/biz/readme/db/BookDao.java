package com.biz.readme.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {
    /**
     * 添加单个对象
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(BookHistory... histories);
    /**
     * 获取所有数据
     */
    @Query("SELECT * FROM book_history")
    List<BookHistory> getAll();

//    @Query("SELECT * FROM book_history WHERE path LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);

    /**
     * 根据对象中的主键删除（主键是自动增长的，无需手动赋值）
     */
    @Delete
    public void delete(BookHistory obj);

}
