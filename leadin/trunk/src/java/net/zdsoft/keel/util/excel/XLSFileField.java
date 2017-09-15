/* 
 * @(#)XLSFileField.java    Created on 2007-3-21
 * Copyright (c) 2007 ZDSoft Networks, Inc. All rights reserved.
 * $Id: XLSFileField.java,v 1.1 2007/04/28 03:46:25 huangwj Exp $
 */
package net.zdsoft.keel.util.excel;

import java.util.Map;

/**
 * XLS文件模板中的字段, 用于描述XLS文件中的表格单元.
 * 
 * @author huangwj
 * @version $Revision: 1.1 $, $Date: 2007/04/28 03:46:25 $
 */
public class XLSFileField {

    public static final int TEXT = 0;
    public static final int NUMBER = 1;
    public static final int DATE = 2;
    public static final int EMAIL = 3;
    public static final int MOBILE = 4;
    public static final int PHONE = 5;
    public static final int POSTCODE = 6;
    public static final int IDCARD = 7;
    public static final int FLOAT = 8; // 浮点型
    public static final int OTHER = 999;

    private String id; // 字段标识
    private String name; // 字段名称
    private int type = TEXT; // 字段类型, 默认文本类型
    private Map<String, Object> range; // 普通字段的取值范围map
    private String defaultValue; // 字段默认值
    private String description; // 字段描述
    private boolean required; // 字段是否必须
    private int minLength; // 字段的值最小允许长度
    private int maxLength; // 字段的值最大允许长度
    private boolean checked;// 字段是否需默认选中

    public XLSFileField() {
    }

    public XLSFileField(String id, String name, int type, String defaultValue,
            String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
        this.description = description;
    }

    public boolean isText() {
        return TEXT == type;
    }

    public boolean isNumber() {
        return NUMBER == type;
    }

    public boolean isFloat() {
        return FLOAT == type;
    }

    public boolean isDate() {
        return DATE == type;
    }

    public boolean isEmail() {
        return EMAIL == type;
    }

    public boolean isMobile() {
        return MOBILE == type;
    }

    public boolean isPhone() {
        return PHONE == type;
    }

    public boolean isPostcode() {
        return POSTCODE == type;
    }

    public boolean isIdCard() {
        return IDCARD == type;
    }

    public boolean isOther() {
        return OTHER == type;
    }

    /**
     * @return Returns the defaultValue.
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue
     *            The defaultValue to set.
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the type.
     */
    public int getType() {
        return type;
    }

    /**
     * @param type
     *            The type to set.
     */
    public void setType(int type) {
        this.type = type;
    }

    public Map<String, Object> getRange() {
        return range;
    }

    public void setRange(Map<String, Object> range) {
        this.range = range;
    }

    /**
     * @return Returns the required.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @param isRequired
     *            The isRequired to set.
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * @return Returns the minLength.
     */
    public int getMinLength() {
        return minLength;
    }

    /**
     * @param minLength
     *            The minLength to set.
     */
    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    /**
     * @return Returns the maxLength.
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * @param maxLength
     *            The maxLength to set.
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @return Returns the checked.
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param checked
     *            The checked to set.
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
