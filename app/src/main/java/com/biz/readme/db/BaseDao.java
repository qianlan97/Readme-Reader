package com.biz.readme.db;

import android.util.Log;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

abstract public class BaseDao<T> {
    /**
     * 添加单个对象
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract public void insert(T obj);

    /**
     * 根据对象中的主键删除（主键是自动增长的，无需手动赋值）
     */
    @Delete
    abstract public void delete(T obj);

    /**
     * 根据对象中的主键更新（主键是自动增长的，无需手动赋值）
     */
    @Update
    abstract public Integer update(T obj);

    Integer deleteAll() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("delete from $tableName");
        return doDeleteAll(query);
    }

    List<T> findAll() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("select * from $tableName");
        return doFindAll(query);
    }

    @RawQuery
    protected abstract List<T> doFindAll(SupportSQLiteQuery query);

    @RawQuery
    protected abstract T doFind(SupportSQLiteQuery query);

    @RawQuery
    protected abstract Integer doDeleteAll(SupportSQLiteQuery query);

    @RawQuery
    protected abstract Integer doDeleteByParams(SupportSQLiteQuery query);

    @RawQuery
    protected abstract List<T> doQueryByLimit(SupportSQLiteQuery query);

    @RawQuery
    protected abstract List<T> doQueryByOrder(SupportSQLiteQuery query);
}
