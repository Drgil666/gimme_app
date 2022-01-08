package com.project.gimme.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.project.gimme.pojo.GroupUser;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "group_user".
 */
public class GroupUserDao extends AbstractDao<GroupUser, Void> {

    public static final String TABLENAME = "group_user";

    /**
     * Properties of entity GroupUser.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property GroupId = new Property(0, Integer.class, "groupId", false, "group_id");
        public final static Property UserId = new Property(1, Integer.class, "userId", false, "user_id");
        public final static Property Type = new Property(2, Integer.class, "type", false, "type");
        public final static Property GroupNick = new Property(3, String.class, "groupNick", false, "group_nick");
    }


    public GroupUserDao(DaoConfig config) {
        super(config);
    }

    public GroupUserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"group_user\" (" + //
                "\"group_id\" INTEGER," + // 0: groupId
                "\"user_id\" INTEGER," + // 1: userId
                "\"type\" INTEGER," + // 2: type
                "\"group_nick\" TEXT);"); // 3: groupNick
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_group_user_group_id_user_id ON \"group_user\"" +
                " (\"group_id\" ASC,\"user_id\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"group_user\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GroupUser entity) {
        stmt.clearBindings();
 
        Integer groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindLong(1, groupId);
        }
 
        Integer userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(2, userId);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(3, type);
        }
 
        String groupNick = entity.getGroupNick();
        if (groupNick != null) {
            stmt.bindString(4, groupNick);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GroupUser entity) {
        stmt.clearBindings();
 
        Integer groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindLong(1, groupId);
        }
 
        Integer userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(2, userId);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(3, type);
        }

        String groupNick = entity.getGroupNick();
        if (groupNick != null) {
            stmt.bindString(4, groupNick);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }

    @Override
    public GroupUser readEntity(Cursor cursor, int offset) {
        GroupUser entity = new GroupUser( //
                cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // groupId
                cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // userId
                cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // type
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // groupNick
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, GroupUser entity, int offset) {
        entity.setGroupId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setType(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setGroupNick(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
    }

    @Override
    protected final Void updateKeyAfterInsert(GroupUser entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }

    @Override
    public Void getKey(GroupUser entity) {
        return null;
    }

    @Override
    public boolean hasKey(GroupUser entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}