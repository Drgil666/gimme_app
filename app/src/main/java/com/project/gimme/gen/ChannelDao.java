package com.project.gimme.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.project.gimme.pojo.Channel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "channel".
*/
public class ChannelDao extends AbstractDao<Channel, Integer> {

    public static final String TABLENAME = "channel";

    /**
     * Properties of entity Channel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Integer.class, "id", true, "id");
        public final static Property OwnerId = new Property(1, Integer.class, "ownerId", false, "owner_id");
        public final static Property Nick = new Property(2, String.class, "nick", false, "nick");
        public final static Property CreateTime = new Property(3, java.util.Date.class, "createTime", false, "create_time");
        public final static Property Description = new Property(4, String.class, "description", false, "description");
    }


    public ChannelDao(DaoConfig config) {
        super(config);
    }
    
    public ChannelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"channel\" (" + //
                "\"id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"owner_id\" INTEGER," + // 1: ownerId
                "\"nick\" TEXT," + // 2: nick
                "\"create_time\" INTEGER," + // 3: createTime
                "\"description\" TEXT);"); // 4: description
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"channel\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Channel entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindLong(2, ownerId);
        }
 
        String nick = entity.getNick();
        if (nick != null) {
            stmt.bindString(3, nick);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(4, createTime.getTime());
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(5, description);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Channel entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindLong(2, ownerId);
        }
 
        String nick = entity.getNick();
        if (nick != null) {
            stmt.bindString(3, nick);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(4, createTime.getTime());
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(5, description);
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }    

    @Override
    public Channel readEntity(Cursor cursor, int offset) {
        Channel entity = new Channel( //
            cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // ownerId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nick
            cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)), // createTime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // description
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Channel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setOwnerId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setNick(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreateTime(cursor.isNull(offset + 3) ? null : new java.util.Date(cursor.getLong(offset + 3)));
        entity.setDescription(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Integer updateKeyAfterInsert(Channel entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public Integer getKey(Channel entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Channel entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
