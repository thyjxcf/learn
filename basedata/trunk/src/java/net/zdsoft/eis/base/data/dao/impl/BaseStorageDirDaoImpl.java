/* 
 * @(#)StorageDirDaoImpl.java    Created on Dec 6, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.data.dao.BaseStorageDirDao;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 6, 2010 1:23:29 PM $
 */
public class BaseStorageDirDaoImpl extends BaseDao<StorageDir> implements BaseStorageDirDao {
    private static final String SQL_INSERT_STORAGEDIR = "INSERT INTO base_storage_dir(id,type,active,preset,"
            + "dir) " + "VALUES(?,?,?,?,?)";

    private static final String SQL_DELETE_STORAGEDIR_BY_IDS = "DELETE FROM base_storage_dir WHERE id IN ";

    private static final String SQL_UPDATE_STORAGEDIR = "UPDATE base_storage_dir SET type=?,active=?,preset=?,"
            + "dir=? WHERE id=?";

    private static final String SQL_FIND_ACTIVE_STORAGEDIR = "SELECT COUNT(1) FROM base_storage_dir WHERE type=? AND active=1 AND preset=0";
    private static final String SQL_FIND_ACTIVE_STORAGEDIR2 = "SELECT COUNT(1) FROM base_storage_dir WHERE type=? AND active=1 AND preset=0 AND id <> ?";

    private static final String SQL_FIND_STORAGEDIRS = "SELECT * FROM base_storage_dir ORDER BY type";

    @Override
    public StorageDir setField(ResultSet rs) throws SQLException {
        StorageDir storageDir = new StorageDir();
        storageDir.setId(rs.getString("id"));
        storageDir.setType(rs.getInt("type"));
        storageDir.setDirType(StorageDir.StorageDirType.valueOf(storageDir.getType()));
        storageDir.setActive(rs.getBoolean("active"));
        storageDir.setPreset(rs.getBoolean("preset"));
        storageDir.setDir(rs.getString("dir"));
        return storageDir;
    }

    public void insertStorageDir(StorageDir storageDir) {
        storageDir.setId(createId());
        update(SQL_INSERT_STORAGEDIR, new Object[] { storageDir.getId(), storageDir.getType(),
                storageDir.isActive(), storageDir.isPreset(), storageDir.getDir() }, new int[] {
                Types.CHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR });
    }

    public void deleteStorageDir(String[] storageDirIds) {
        updateForInSQL(SQL_DELETE_STORAGEDIR_BY_IDS, null, storageDirIds);
    }

    public void updateStorageDir(StorageDir storageDir) {
        update(SQL_UPDATE_STORAGEDIR, new Object[] { storageDir.getType(), storageDir.isActive(),
                storageDir.isPreset(), storageDir.getDir(), storageDir.getId() }, new int[] {
                Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.CHAR });
    }

    public boolean isRepeatActiveDir(int type, String id) {
        int rtn = 0;
        if (StringUtils.isBlank(id)) {
            rtn = queryForInt(SQL_FIND_ACTIVE_STORAGEDIR, new Object[] { Integer.valueOf(type) });
        } else {
            rtn = queryForInt(SQL_FIND_ACTIVE_STORAGEDIR2,
                    new Object[] { Integer.valueOf(type), id });
        }
        return rtn > 0 ? true : false;
    }

    public List<StorageDir> getStorageDirs() {
        return query(SQL_FIND_STORAGEDIRS, null, null, new MultiRow());
    }

}
