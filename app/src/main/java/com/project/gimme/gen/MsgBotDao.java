package com.project.gimme.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.project.gimme.pojo.MsgBot;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "msg_bot".
 */
public class MsgBotDao extends AbstractDao<MsgBot, Integer> {

    public static final String TABLENAME = "msg_bot";

    /**
     * Properties of entity MsgBot.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Integer.class, "id", true, "id");
        public final static Property Text = new Property(1, String.class, "text", false, "text");
        public final static Property GroupId = new Property(2, Integer.class, "groupId", false, "group_id");
        public final static Property Cron = new Property(3, String.class, "cron", false, "cron");
    }


    public MsgBotDao(DaoConfig config) {
        super(config);
    }

    public MsgBotDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"msg_bot\" (" + //
                "\"id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"text\" TEXT," + // 1: text
                "\"group_id\" INTEGER," + // 2: groupId
                "\"cron\" TEXT);"); // 3: cron
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"msg_bot\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MsgBot entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(2, text);
        }
 
        Integer groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindLong(3, groupId);
        }
 
        String cron = entity.getCron();
        if (cron != null) {
            stmt.bindString(4, cron);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MsgBot entity) {
        stmt.clearBindings();
 
        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String text = entity.getText();
        if (text != null) {
            stmt.bindString(2, text);
        }
 
        Integer groupId = entity.getGroupId();
        if (groupId != null) {
            stmt.bindLong(3, groupId);
        }

        String cron = entity.getCron();
        if (cron != null) {
            stmt.bindString(4, cron);
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }

    @Override
    public MsgBot readEntity(Cursor cursor, int offset) {
        MsgBot entity = new MsgBot( //
                cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // text
                cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // groupId
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // cron
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, MsgBot entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setText(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setGroupId(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setCron(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
    }

    @Override
    protected final Integer updateKeyAfterInsert(MsgBot entity, long rowId) {
        return entity.getId();
    }

    @Override
    public Integer getKey(MsgBot entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MsgBot entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
