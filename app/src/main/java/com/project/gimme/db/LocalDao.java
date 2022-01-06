package com.project.gimme.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.project.gimme.pojo.Channel;
import com.project.gimme.pojo.User;

import java.util.List;

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

    /**
     * 创建频道
     *
     * @param channel 被创建的频道类
     * @return 是否成功
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Boolean createChannel(Channel channel);

    /**
     * 更新频道
     *
     * @param channel 要更新的频道
     * @return 影响行数
     */
    @Update
    Long updateChannel(Channel channel);

    /**
     * 通过id获取频道
     *
     * @param id 频道id
     * @return 频道
     */
    @Query("select * from channel where id=:id")
    Channel getChannel(Integer id);

    /**
     * 批量删除频道
     *
     * @param idList id列表
     * @return 影响行数
     */
    @Query("delete from channel where id in :idList")
    Long deleteChannel(List<Integer> idList);
}
