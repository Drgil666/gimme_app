package com.project.gimme.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.project.gimme.pojo.PersonalMsg;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "personal_msg".
 */
public class PersonalMsgDao extends AbstractDao<PersonalMsg, Integer> {

    public static final String TABLENAME = "personal_msg";

    /**
     * Properties of entity PersonalMsg.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Integer.class, "id", true, "id");
        public final static Property Type = new Property(1, Integer.class, "type", false, "type");
        public final static Property OwnerId = new Property(2, Integer.class, "ownerId", false, "owner_id");
        public final static Property OperatorId = new Property(3, Integer.class, "operatorId", false, "operator_id");
        public final static Property ObjectId = new Property(4, Integer.class, "objectId", false, "object_id");
    }


    public PersonalMsgDao(DaoConfig config) {
        super(config);
    }

    public PersonalMsgDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"personal_msg\" (" + //
                "\"id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"type\" INTEGER," + // 1: type
                "\"owner_id\" INTEGER," + // 2: ownerId
                "\"operator_id\" INTEGER," + // 3: operatorId
                "\"object_id\" INTEGER);"); // 4: objectId
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"personal_msg\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PersonalMsg entity) {
        stmt.clearBindings();

        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(2, type);
        }

        Integer ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindLong(3, ownerId);
        }

        Integer operatorId = entity.getOperatorId();
        if (operatorId != null) {
            stmt.bindLong(4, operatorId);
        }

        Integer objectId = entity.getObjectId();
        if (objectId != null) {
            stmt.bindLong(5, objectId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PersonalMsg entity) {
        stmt.clearBindings();

        Integer id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(2, type);
        }

        Integer ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindLong(3, ownerId);
        }

        Integer operatorId = entity.getOperatorId();
        if (operatorId != null) {
            stmt.bindLong(4, operatorId);
        }

        Integer objectId = entity.getObjectId();
        if (objectId != null) {
            stmt.bindLong(5, objectId);
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0);
    }

    @Override
    public PersonalMsg readEntity(Cursor cursor, int offset) {
        PersonalMsg entity = new PersonalMsg( //
                cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // type
                cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // ownerId
                cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // operatorId
                cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4) // objectId
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, PersonalMsg entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setType(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setOwnerId(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setOperatorId(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setObjectId(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
    }

    @Override
    protected final Integer updateKeyAfterInsert(PersonalMsg entity, long rowId) {
        return entity.getId();
    }

    @Override
    public Integer getKey(PersonalMsg entity) {
        if (entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PersonalMsg entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

}
