package com.project.gimme.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.project.gimme.pojo.User;

/**
 * @author DrGilbert
 * @date 2022/1/5 16:50
 */
@Dao
public interface LocalDao {
    /**
     * 插入用户
     *
     * @param user 被插入的用户
     * @return 是否成功
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Boolean insertUser(User user);

    /**
     * 更新用户
     *
     * @param user 被更新的用户
     * @return 影响行数
     */
    @Update
    Long updateUser(User user);

    /**
     * 通过id获取用户信息
     *
     * @param id 用户id
     * @return 用户
     */
    @Query("select * from user where id= :id")
    User getUser(Integer id);
}
