/*
 * Created on 2004-5-31
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.zdsoft.keel.page;

/**
 * 表单域定义
 * 
 * @author liangxiao
 * @version $Revision: 1.4 $, $Date: 2007/01/12 02:23:30 $
 */
public class HtmlField {
    /**
     * 文本框
     */
    public final static int TEXT = 0;// input-text
    /**
     * 密码框
     */
    public final static int PASSWORD = 1;// input-password
    /**
     * 文本区域
     */
    public final static int TEXTAREA = 2;// textarea
    /**
     * 复选框
     */
    public final static int CHECKBOX = 3;// input-checkbox
    /**
     * 单选框
     */
    public final static int RADIO = 4;// input-radio
    /**
     * 下拉框
     */
    public final static int SELECT = 5;// select
    /**
     * 隐藏域
     */
    public final static int HIDDEN = 6;// hidden

    private String name = null;
    private int type;
    private String value = null;
    private boolean isDisabled = false;

    /**
     * 构造方法
     * 
     * @param name
     *            名称
     * @param type
     *            类型
     * @param value
     *            值
     */
    public HtmlField(String name, int type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    /**
     * 构造方法
     * 
     * @param name
     *            名称
     * @param type
     *            类型
     * @param value
     *            值
     * @param isDisabled
     *            是否处在禁用状态
     */
    public HtmlField(String name, int type, String value, boolean isDisabled) {
        this(name, type, value);
        this.isDisabled = isDisabled;
    }

    /**
     * 取得表单域的名称
     * 
     * @return 表单域的名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 取得表单域的类型
     * 
     * @return 表单域的类型
     */
    public int getType() {
        return this.type;
    }

    /**
     * 取得表单域的值
     * 
     * @return 表单域的值
     */
    public String getValue() {
        return this.value;
    }

    /**
     * 判断是否处在禁用状态
     * 
     * @return 是true，否则false
     */
    public boolean isDisabled() {
        return this.isDisabled;
    }

    /**
     * 把表单域的类型的字符串名称转换成整型，比如：text转换成HtmlField.TEXT
     * 
     * @param type
     *            类型名称
     * @return 类型
     */
    public static int getFieldType(String type) {
        if ("text".equalsIgnoreCase(type)) {
            return TEXT;
        }
        if ("password".equalsIgnoreCase(type)) {
            return PASSWORD;
        }
        if ("textarea".equalsIgnoreCase(type)) {
            return TEXTAREA;
        }
        if ("checkbox".equalsIgnoreCase(type)) {
            return CHECKBOX;
        }
        if ("radio".equalsIgnoreCase(type)) {
            return RADIO;
        }
        if ("select".equalsIgnoreCase(type)) {
            return SELECT;
        }
        if ("hidden".equalsIgnoreCase(type)) {
            return HIDDEN;
        }
        throw new IllegalArgumentException("Unknow field type");
    }

}
