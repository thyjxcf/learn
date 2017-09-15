/* 
 * @(#)ImportDataTemplateDaoImpl.java    Created on Aug 10, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.leadin.dataimport.entity.ImportDataTemplate;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Aug 10, 2010 10:15:06 AM $
 */
public class ImportDataTemplateDaoImpl extends BasicDAO implements ImportDataTemplateDao {

    private static final String SQL_INSERT_TEMPLATE = "INSERT INTO sys_import_template(id,unit_id,init_file,"
            + "object_name,field_name,creation_time,modify_time) VALUES(?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_TEMPLATE = "DELETE FROM sys_import_template "
            + "WHERE  init_file = ? AND object_name = ? AND unit_id = ?";

    private static final String SQL_FIND_TEMPLATES_BY_UNITID = "SELECT * FROM sys_import_template "
            + "WHERE init_file = ? AND object_name = ? AND unit_id IN ";

    private ImportDataTemplate setField(ResultSet rs) throws SQLException {
        ImportDataTemplate template = new ImportDataTemplate();
        template.setId(rs.getString("id"));
        template.setUnitId(rs.getString("unit_id"));
        template.setInitFile(rs.getString("init_file"));
        template.setObjectName(rs.getString("object_name"));
        template.setFieldName(rs.getString("field_name"));
        template.setCreationTime(rs.getTimestamp("creation_time"));
        template.setModifyTime(rs.getTimestamp("modify_time"));
        return template;
    }

    public void saveTemplates(String initFile, String objectName, String unitId,
            List<ImportDataTemplate> templates) {
        // 先删除
        update(SQL_DELETE_TEMPLATE, new Object[] { initFile, objectName, unitId });

        // 再插入
        List<Object[]> list = new ArrayList<Object[]>();
        for (ImportDataTemplate t : templates) {
            t.setCreationTime(new Date());
            t.setModifyTime(new Date());
            list.add(new Object[] { t.getId(), unitId, initFile, objectName, t.getFieldName(),
                    t.getCreationTime(), t.getModifyTime() });
        }

        batchUpdate(SQL_INSERT_TEMPLATE, list, new int[] { Types.CHAR, Types.CHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP });

    }

    public List<ImportDataTemplate> findTemplates(String initFile, String objectName,
            String... unitIds) {
        return queryForInSQL(SQL_FIND_TEMPLATES_BY_UNITID, new Object[] { initFile, objectName },
                unitIds, new MultiRowMapper<ImportDataTemplate>() {
                    @Override
                    public ImportDataTemplate mapRow(ResultSet rs, int arg1) throws SQLException {
                        return setField(rs);
                    }
                });
    }

}
