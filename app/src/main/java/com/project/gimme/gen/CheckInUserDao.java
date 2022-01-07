package com.project.gimme.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.project.gimme.pojo.CheckInUser;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "checkin_user".
 */
public class CheckInUserDao extends AbstractDao<CheckInUser, Void> {

    public static final String TABLENAME = "checkin_user";

    /**
     * Properties of entity CheckInUser.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property UserId = new Property(0, Integer.class, "userId", false, "user_id");
        public final static Property CheckInId = new Property(1, Integer.class, "checkInId", false, "checkin_id");
        public final static Property Timestamp = new Property(2, java.util.Date.class, "timestamp", false, "timestamp");
    }


    public CheckInUserDao(DaoConfig config) {
        super(config);
    }

    public CheckInUserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"checkin_user\" (" + //
                "\"user_id\" INTEGER," + // 0: userId
                "\"checkin_id\" INTEGER," + // 1: checkInId
                "\"timestamp\" INTEGER);"); // 2: timestamp
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_checkin_user_user_id_checkin_id ON \"checkin_user\"" +
                " (\"user_id\" ASC,\"checkin_id\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"checkin_user\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CheckInUser entity) {
        stmt.clearBindings();
 
        Integer userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(1, userId);
        }
 
        Integer checkInId = entity.getCheckInId();
        if (checkInId != null) {
            stmt.bindLong(2, checkInId);
        }
 
        java.util.Date timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindLong(3, timestamp.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CheckInUser entity) {
        stmt.clearBindings();
 
        Integer userId = entity.getUserId();
        if (userId != null) {
            stmt.bindLong(1, userId);
        }
 
        Integer checkInId = entity.getCheckInId();
        if (checkInId != null) {
            stmt.bindLong(2, checkInId);
        }

        java.util.Date timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindLong(3, timestamp.getTime());
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }

    @Override
    public CheckInUser readEntity(Cursor cursor, int offset) {
        CheckInUser entity = new CheckInUser( //
                cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // userId
                cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // checkInId
                cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)) // timestamp
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, CheckInUser entity, int offset) {
        entity.setUserId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setCheckInId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setTimestamp(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
    }

    @Override
    protected final Void updateKeyAfterInsert(CheckInUser entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }

    @Override
    public Void getKey(CheckInUser entity) {
        return null;
    }

    @Override
    public boolean hasKey(CheckInUser entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
