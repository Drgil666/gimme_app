package com.project.gimme.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.project.gimme.pojo.ChatFile;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "group_file".
 */
public class GroupFileDao extends AbstractDao<ChatFile, Integer> {

    public static final String TABLENAME = "group_file";

    /**
     * Properties of entity GroupFile.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Integer.class, "id", true, "id");
        public final static Property OwnerId = new Property(1, Integer.class, "ownerId", false, "owner_id");
        public final static Property GroupId = new Property(2, Integer.class, "groupId", false, "group_id");
        public final static Property MongoId = new Property(3, String.class, "mongoId", false, "mongo_id");
        public final static Property Filename = new Property(4, String.class, "filename", false, "filename");
    }


    public GroupFileDao(DaoConfig config) {
        super(config);
    }

    public GroupFileDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"group_file\" (" + //
                "\"id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"owner_id\" INTEGER," + // 1: ownerId
                "\"group_id\" INTEGER," + // 2: groupId
                "\"mongo_id\" TEXT," + // 3: mongoId
                "\"filename\" TEXT);"); // 4: filename
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"group_file\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChatFile entity) {
        stmt.clearBindings();

        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Integer ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindLong(2, ownerId);
        }
 
        Integer groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindLong(3, groupId);
        }
 
        String mongoId = entity.getMongoId();
        if (mongoId != null) {
            stmt.bindString(4, mongoId);
        }
 
        String filename = entity.getFilename();
        if (filename != null) {
            stmt.bindString(5, filename);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChatFile entity) {
        stmt.clearBindings();

        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Integer ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindLong(2, ownerId);
        }
 
        Integer groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindLong(3, groupId);
        }
 
        String mongoId = entity.getMongoId();
        if (mongoId != null) {
            stmt.bindString(4, mongoId);
        }

        String filename = entity.getFilename();
        if (filename != null) {
            stmt.bindString(5, filename);
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }

    @Override
    public ChatFile readEntity(Cursor cursor, int offset) {
        ChatFile entity = new ChatFile( //
                cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // ownerId
                cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // groupId
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mongoId
                cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // filename
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, ChatFile entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setOwnerId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setGroupId(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setMongoId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setFilename(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
    }

    @Override
    protected final Integer updateKeyAfterInsert(ChatFile entity, long rowId) {
        return entity.getId();
    }

    @Override
    public Integer getKey(ChatFile entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ChatFile entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
