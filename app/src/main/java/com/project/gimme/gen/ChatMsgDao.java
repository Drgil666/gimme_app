package com.project.gimme.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.project.gimme.pojo.ChatMsg;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "chat_msg".
 */
public class ChatMsgDao extends AbstractDao<ChatMsg, Integer> {

    public static final String TABLENAME = "chat_msg";

    /**
     * Properties of entity ChatMsg.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Integer.class, "id", true, "id");
        public final static Property Text = new Property(1, String.class, "text", false, "text");
        public final static Property Type = new Property(2, Integer.class, "type", false, "type");
        public final static Property ObjectId = new Property(3, Integer.class, "objectId", false, "object_id");
        public final static Property TimeStamp = new Property(4, java.util.Date.class, "timeStamp", false, "timestamp");
        public final static Property OwnerId = new Property(5, Integer.class, "ownerId", false, "owner_id");
    }


    public ChatMsgDao(DaoConfig config) {
        super(config);
    }

    public ChatMsgDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"chat_msg\" (" + //
                "\"id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"text\" TEXT," + // 1: text
                "\"type\" INTEGER," + // 2: type
                "\"object_id\" INTEGER," + // 3: objectId
                "\"timestamp\" INTEGER," + // 4: timeStamp
                "\"owner_id\" INTEGER);"); // 5: ownerId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"chat_msg\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChatMsg entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(2, text);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(3, type);
        }
 
        Integer objectId = entity.getObjectId();
        if (objectId != null) {
            stmt.bindLong(4, objectId);
        }
 
        java.util.Date timeStamp = entity.getTimeStamp();
        if (timeStamp != null) {
            stmt.bindLong(5, timeStamp.getTime());
        }
 
        Integer ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindLong(6, ownerId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChatMsg entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(2, text);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(3, type);
        }
 
        Integer objectId = entity.getObjectId();
        if (objectId != null) {
            stmt.bindLong(4, objectId);
        }
 
        java.util.Date timeStamp = entity.getTimeStamp();
        if (timeStamp != null) {
            stmt.bindLong(5, timeStamp.getTime());
        }

        Integer ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindLong(6, ownerId);
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }

    @Override
    public ChatMsg readEntity(Cursor cursor, int offset) {
        ChatMsg entity = new ChatMsg( //
                cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // text
                cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // type
                cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // objectId
                cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // timeStamp
                cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5) // ownerId
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, ChatMsg entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setText(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setType(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setObjectId(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setTimeStamp(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setOwnerId(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
    }

    @Override
    protected final Integer updateKeyAfterInsert(ChatMsg entity, long rowId) {
        return entity.getId();
    }

    @Override
    public Integer getKey(ChatMsg entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ChatMsg entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}