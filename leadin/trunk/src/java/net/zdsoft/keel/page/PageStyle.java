/*
 * Created on 2005-1-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.keel.page;

/**
 * 消息回显样式定义
 * 
 * @author liangxiao
 * @version $Revision: 1.3 $, $Date: 2007/01/12 02:23:30 $
 */
public class PageStyle {

    private String actionErrorsHead = "<ul>";
    private String actionErrorsFoot = "</ul>";
    private String actionErrorHead = "<li>";
    private String actionErrorFoot = "</li>";

    private String actionMessagesHead = "<ul>";
    private String actionMessagesFoot = "</ul>";
    private String actionMessageHead = "<li>";
    private String actionMessageFoot = "</li>";

    private String fieldErrorsHead = "<ul>";
    private String fieldErrorsFoot = "</ul>";
    private String fieldErrorHead = "<li>";
    private String fieldErrorFoot = "</li>";

    private String successMessageHead = "<span>";
    private String successMessageFoot = "</span>";

    private String successParameterName = "state";
    private String successParameterValue = "1";

    /**
     * 构造方法
     */
    public PageStyle() {
    }

    /**
     * 设置actionError的样式
     * 
     * @param actionErrorsHead
     * @param actionErrorHead
     * @param actionErrorFoot
     * @param actionErrorsFoot
     */
    public void setActionErrorStyle(String actionErrorsHead,
            String actionErrorHead, String actionErrorFoot,
            String actionErrorsFoot) {
        this.actionErrorsHead = actionErrorsHead;
        this.actionErrorHead = actionErrorHead;
        this.actionErrorFoot = actionErrorFoot;
        this.actionErrorsFoot = actionErrorsFoot;
    }

    /**
     * 设置actionMessage的样式
     * 
     * @param actionMessagesHead
     * @param actionMessageHead
     * @param actionMessageFoot
     * @param actionMessagesFoot
     */
    public void setActionMessageStyle(String actionMessagesHead,
            String actionMessageHead, String actionMessageFoot,
            String actionMessagesFoot) {
        this.actionMessagesHead = actionMessagesHead;
        this.actionMessageHead = actionMessageHead;
        this.actionMessageFoot = actionMessageFoot;
        this.actionMessagesFoot = actionMessagesFoot;
    }

    /**
     * 设置fieldError的样式
     * 
     * @param fieldErrorsHead
     * @param fieldErrorHead
     * @param fieldErrorFoot
     * @param fieldErrorsFoot
     */
    public void setFieldErrorStyle(String fieldErrorsHead,
            String fieldErrorHead, String fieldErrorFoot, String fieldErrorsFoot) {
        this.fieldErrorsHead = fieldErrorsHead;
        this.fieldErrorHead = fieldErrorHead;
        this.fieldErrorFoot = fieldErrorFoot;
        this.fieldErrorsFoot = fieldErrorsFoot;
    }

    /**
     * 设置成功消息的样式
     * 
     * @param successMessageHead
     * @param successMessageFoot
     */
    public void setSuccessMessageStyle(String successMessageHead,
            String successMessageFoot) {
        this.successMessageHead = successMessageHead;
        this.successMessageFoot = successMessageFoot;
    }

    /**
     * 设置表示成功的参数，参数是通过GET请求传递的，比如state=1
     * 
     * @param successParameterName
     * @param successParameterValue
     */
    public void setSuccessParameter(String successParameterName,
            String successParameterValue) {
        this.successParameterName = successParameterName;
        this.successParameterValue = successParameterValue;
    }

    public String getActionErrorFoot() {
        return actionErrorFoot;
    }

    public String getActionErrorHead() {
        return actionErrorHead;
    }

    public String getActionErrorsFoot() {
        return actionErrorsFoot;
    }

    public String getActionErrorsHead() {
        return actionErrorsHead;
    }

    public String getActionMessageFoot() {
        return actionMessageFoot;
    }

    public String getActionMessageHead() {
        return actionMessageHead;
    }

    public String getActionMessagesFoot() {
        return actionMessagesFoot;
    }

    public String getActionMessagesHead() {
        return actionMessagesHead;
    }

    public String getFieldErrorFoot() {
        return fieldErrorFoot;
    }

    public String getFieldErrorHead() {
        return fieldErrorHead;
    }

    public String getFieldErrorsFoot() {
        return fieldErrorsFoot;
    }

    public String getFieldErrorsHead() {
        return fieldErrorsHead;
    }

    public String getSuccessMessageFoot() {
        return successMessageFoot;
    }

    public String getSuccessMessageHead() {
        return successMessageHead;
    }

    public String getSuccessParameterName() {
        return successParameterName;
    }

    public String getSuccessParameterValue() {
        return successParameterValue;
    }
}