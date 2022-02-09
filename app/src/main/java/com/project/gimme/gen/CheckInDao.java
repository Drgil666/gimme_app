package com.project.gimme.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.project.gimme.pojo.CheckIn;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "checkin".
 */
public class CheckInDao extends AbstractDao<CheckIn, Integer> {

    public static final String TABLENAME = "checkin";

    /**
     * Properties of entity CheckIn.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Integer.class, "id", true, "id");
        public final static Property GroupId = new Property(1, Integer.class, "groupId", false, "group_id");
        public final static Property Address = new Property(2, String.class, "address", false, "address");
        public final static Property Type = new Property(3, Integer.class, "type", false, "type");
    }


    public CheckInDao(DaoConfig config) {
        super(config);
    }

    public CheckInDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"checkin\" (" + //
                "\"id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"group_id\" INTEGER," + // 1: groupId
                "\"address\" TEXT," + // 2: address
                "\"type\" INTEGER);"); // 3: type
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"checkin\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CheckIn entity) {
        stmt.clearBindings();

        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Integer groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindLong(2, groupId);
        }

        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(3, address);
        }

        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(4, type);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CheckIn entity) {
        stmt.clearBindings();

        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Integer groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindLong(2, groupId);
        }

        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(3, address);
        }

        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(4, type);
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }

    @Override
    public CheckIn readEntity(Cursor cursor, int offset) {
        CheckIn entity = new CheckIn( //
                cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // groupId
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // address
                cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3) // type
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, CheckIn entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setGroupId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setAddress(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setType(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
    }

    @Override
    protected final Integer updateKeyAfterInsert(CheckIn entity, long rowId) {
        return entity.getId();
    }

    @Override
    public Integer getKey(CheckIn entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CheckIn entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

}
