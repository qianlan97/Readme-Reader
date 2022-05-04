package com.biz.readme.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book_history")
public class BookHistory {
    public int id;
    public String name;//名称
    @PrimaryKey
    @NonNull
    public String path;//路径
    public int index;//读到第几页
    public int pages;//总页数
    public int readNum;//阅读打开次数
//    public String type;//文件格式

}
