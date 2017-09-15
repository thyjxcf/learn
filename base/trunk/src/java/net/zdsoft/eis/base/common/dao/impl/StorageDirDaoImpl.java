/* 
 * @(#)StorageDirDaoImpl.java    Created on Dec 6, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.StorageDirDao;
import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.frame.client.BaseDao;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 6, 2010 1:23:29 PM $
 */
public class StorageDirDaoImpl extends BaseDao<StorageDir> implements StorageDirDao {
    private static final String SQL_UPDATE_STORAGEDIR_BY_PRESET_TYPE = "UPDATE base_storage_dir "
            + "SET dir=?,active=? WHERE type=? AND preset=?";

    private static final String SQL_FIND_STORAGEDIR_BY_ID = "SELECT * FROM base_storage_dir WHERE id=?";
    private static final String SQL_FIND_STORAGEDIR_BY_TYPE_ACTIVE = "SELECT * FROM base_storage_dir WHERE type=? AND active=1";
    private static final String SQL_FIND_STORAGEDIR_BY_TYPE_PRESET = "SELECT * FROM base_storage_dir WHERE type=? AND preset=1";
    private static final String SQL_FIND_STORAGEDIRS_BY_IDS = "SELECT * FROM base_storage_dir WHERE id IN";

    @Override
    public StorageDir setField(ResultSet rs) throws SQLException {
        StorageDir storageDir = new StorageDir();
        storageDir.setId(rs.getString("id"));
        storageDir.setType(rs.getInt("type"));
        storageDir.setActive(rs.getBoolean("active"));
        storageDir.setPreset(rs.getBoolean("preset"));
        storageDir.setDir(rs.getString("dir"));
        return storageDir;
    }

    public void updatePresetDir(StorageDir storageDir) {
        update(SQL_UPDATE_STORAGEDIR_BY_PRESET_TYPE, new Object[] { storageDir.getDir(),
                storageDir.isActive(), storageDir.getType(), storageDir.isPreset() }, new int[] {
                Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER });
    }

    public StorageDir getStorageDir(String storageDirId) {
        return query(SQL_FIND_STORAGEDIR_BY_ID, storageDirId, new SingleRow());
    }

    public StorageDir getActiveStorageDir(int type) {
        return query(SQL_FIND_STORAGEDIR_BY_TYPE_ACTIVE, new Object[] { Integer.valueOf(type) },
                new SingleRow());
    }

    public StorageDir getPresetStorageDir(int type) {
        return query(SQL_FIND_STORAGEDIR_BY_TYPE_PRESET, new Object[] { Integer.valueOf(type) },
                new SingleRow());
    }    
    
    public Map<String, StorageDir> getStorageDirs(String[] storageDirIds) {
        return queryForInSQL(SQL_FIND_STORAGEDIRS_BY_IDS, null, storageDirIds, new MapRow());
    }

}
