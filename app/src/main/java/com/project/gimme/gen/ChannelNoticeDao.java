package com.project.gimme.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.project.gimme.pojo.ChannelNotice;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "channel_notice".
 */
public class ChannelNoticeDao extends AbstractDao<ChannelNotice, Integer> {

    public static final String TABLENAME = "channel_notice";

    /**
     * Properties of entity ChannelNotice.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Integer.class, "id", true, "id");
        public final static Property Type = new Property(1, Integer.class, "type", false, "type");
        public final static Property ChannelId = new Property(2, Integer.class, "channelId", false, "channel_id");
        public final static Property Text = new Property(3, String.class, "text", false, "text");
        public final static Property CreateTime = new Property(4, java.util.Date.class, "createTime", false, "CREATE_TIME");
    }


    public ChannelNoticeDao(DaoConfig config) {
        super(config);
    }

    public ChannelNoticeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"channel_notice\" (" + //
                "\"id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"type\" INTEGER," + // 1: type
                "\"channel_id\" INTEGER," + // 2: channelId
                "\"text\" TEXT," + // 3: text
                "\"CREATE_TIME\" INTEGER);"); // 4: createTime
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_channel_notice_id_DESC ON \"channel_notice\"" +
                " (\"id\" DESC);");
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"channel_notice\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChannelNotice entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(2, type);
        }
 
        Integer channelId = entity.getChannelId();
        if (channelId != null) {
            stmt.bindLong(3, channelId);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(4, text);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(5, createTime.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChannelNotice entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(2, type);
        }
 
        Integer channelId = entity.getChannelId();
        if (channelId != null) {
            stmt.bindLong(3, channelId);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(4, text);
        }

        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(5, createTime.getTime());
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }

    @Override
    public ChannelNotice readEntity(Cursor cursor, int offset) {
        ChannelNotice entity = new ChannelNotice( //
                cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // type
                cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // channelId
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // text
                cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)) // createTime
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, ChannelNotice entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setType(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setChannelId(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setText(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCreateTime(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
    }

    @Override
    protected final Integer updateKeyAfterInsert(ChannelNotice entity, long rowId) {
        return entity.getId();
    }

    @Override
    public Integer getKey(ChannelNotice entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ChannelNotice entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
