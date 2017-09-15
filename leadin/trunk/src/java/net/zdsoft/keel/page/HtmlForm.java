/*
 * Created on 2004-4-28
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.zdsoft.keel.page;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.keel.util.Validators;

/**
 * 表单定义
 * 
 * @author liangxiao
 * @version $Revision: 1.4 $, $Date: 2007/01/12 02:23:30 $
 */
public class HtmlForm {

    private List<HtmlField> fields = null;
    private String formName = "forms[0]";

    /**
     * 构造方法
     */
    public HtmlForm() {
        fields = new ArrayList<HtmlField>();
    }

    /**
     * 构造方法
     * 
     * @param formName
     *            表单的名称
     */
    public HtmlForm(String formName) {
        this();
        setForm(formName);
    }

    /**
     * 设置表单的名称
     * 
     * @param formName
     *            表单的名称
     */
    public void setForm(String formName) {
        this.formName = formName;
    }

    /**
     * 增加一个表单域
     * 
     * @param name
     *            名称
     * @param type
     *            类型
     * @param value
     *            值
     */
    public void add(String name, int type, String value) {
        if (value != null) {
            fields.add(new HtmlField(name, type, value));
        }
    }

    /**
     * 增加一个表单域
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
    public void add(String name, int type, String value, boolean isDisabled) {
        if (value != null) {
            fields.add(new HtmlField(name, type, value, isDisabled));
        }
    }

    /**
     * 生成填充表单的脚本
     * 
     * @return 填充表单的脚本
     */
    public String toScript() {
        if (fields.size() == 0) {
            return "";
        }

        StringBuffer script = new StringBuffer(
                "<script language=\"javascript\">\n");

        HtmlField field = null;
        String fieldName = null;
        int fieldType;
        String fieldValue = null;
        boolean isDisabled = false;

        for (int i = 0; i < fields.size(); i++) {
            field = (HtmlField) fields.get(i);
            fieldName = ("document." + formName + "." + field.getName());
            fieldType = field.getType();
            fieldValue = field.getValue();
            isDisabled = field.isDisabled();

            if (fieldType == HtmlField.TEXT || fieldType == HtmlField.TEXTAREA
                    || fieldType == HtmlField.HIDDEN) {
                fieldValue = scriptFilter(fieldValue);
                script.append(fieldName + ".value = \"" + fieldValue + "\";\n");
                if (isDisabled) {
                    script.append(fieldName + ".disabled = true ;\n");
                }
            }
            else if (fieldType == HtmlField.SELECT) {
                script.append(fieldName + ".value = \"" + fieldValue + "\";\n");
                if (isDisabled) {
                    script.append(fieldName + ".disabled = true;\n");
                }
            }
            else if (fieldType == HtmlField.CHECKBOX) {
                if (Validators.isEmpty(fieldValue)) {
                    script.append(fieldName + ".checked = false;\n");
                }
                else {
                    script.append(fieldName + ".checked = true;\n");
                }
                if (isDisabled) {
                    script.append(fieldName + ".disabled = true;\n");
                }
            }
            else if (fieldType == HtmlField.RADIO) {
                script.append("if (" + fieldName + ".length) {\n");
                script.append("  for (var i = 0; i < " + fieldName
                        + ".length; i++) {\n");
                if (fieldValue != null) {
                    script.append("    if (" + fieldName + "[i].value == \""
                            + fieldValue + "\") {\n");
                    script.append("      " + fieldName
                            + "[i].checked = true;\n");
                    if (isDisabled) {
                        script.append("      " + fieldName
                                + "[i].disabled = true;\n");
                    }
                    script.append("    }\n");
                }
                else {
                    script
                            .append("    " + fieldName
                                    + "[i].checked = false;\n");
                    if (isDisabled) {
                        script.append("    " + fieldName
                                + "[i].disabled = true;\n");
                    }
                }
                script.append("  }\n");
                script.append("}\n");
                script.append("else {\n");
                if (fieldValue != null) {
                    script.append("  if (" + fieldName + ".value == \""
                            + fieldValue + "\") {\n");
                    script.append("    " + fieldName + ".checked = true;\n");
                    if (isDisabled) {
                        script.append("    " + fieldName
                                + ".disabled = true;\n");
                    }
                    script.append("  }\n");
                }
                else {
                    script.append("  " + fieldName + ".checked = false;\n");
                    if (isDisabled) {
                        script.append("  " + fieldName + ".disabled = true;\n");
                    }
                }
                script.append("}\n");
            }
        }
        script.append("</script>");

        return script.toString();
    }

    private static String scriptFilter(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        return str;
    }

}