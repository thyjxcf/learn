/* 
 * @(#)XLSFileTemplate.java    Created on 2007-3-21
 * Copyright (c) 2007 ZDSoft Networks, Inc. All rights reserved.
 * $Id: XLSFileTemplate.java,v 1.1 2007/04/28 03:46:25 huangwj Exp $
 */
package net.zdsoft.keel.util.excel;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 用于解析XLS文件的模板类, 包含了所有合法的字段.这些字段名称和XLS文件中的首行标题列名相对应,<br>
 * 解析程序应该根据此模板来解析文件, 获取那些字段列的值.
 * 
 * @author huangwj
 * @version $Revision: 1.1 $, $Date: 2007/04/28 03:46:25 $
 */
public class XLSFileTemplate implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -3702483315094979983L;

    private Set<String> requiredNames;
    private Map<String, XLSFileField> fields;

    public XLSFileTemplate() {
        requiredNames = new HashSet<String>();
        fields = new LinkedHashMap<String, XLSFileField>();
    }

    public void addField(XLSFileField field) {
        if (field.isRequired()) {
            requiredNames.add(field.getName());
        }
        fields.put(field.getName(), field);
    }

    /**
     * 获取模板中的所有字段名称.
     * 
     * @return 字段名称
     */
    public Set<String> getAllFieldNames() {
        return fields.keySet();
    }

    public Collection<XLSFileField> getAllFields() {
        return fields.values();
    }

    public XLSFileField getField(String name) {
        return fields.get(name);
    }

    /**
     * 获取模板中的属性为必须的字段名称.
     * 
     * @return 字段名称
     */
    public Set<String> getRequiredFieldNames() {
        return requiredNames;
    }

}
