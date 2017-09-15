package net.zdsoft.eis.frame.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* 
 * 执行某个业务操作后，反回页面给用户的提示信息封装类，页面上可以得到此类的实例显示提示内容
 *	
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: PromptMessageDto.java,v 1.6 2007/02/07 09:41:55 zhanghh Exp $
 */
public class PromptMessageDto implements Serializable {
    private static final long serialVersionUID = 1L;

    // 操作成功还是失败，true成功；false失败
    private boolean operateSuccess;

    // 提示信息
    private String promptMessage;

    // 错误信息
    private String errorMessage;

    // 出错字段及提示信息
    private Map<String, List<String>> fieldErrorMap;

    // 提示后的下一步操作（成员是String[]，一般String[0]表示操作名称，String[1]表示Action或跳转页面）
    private List<String[]> operations;

    // 提示页面打开另外一个页面（即另外一个action）时，要传递的初始值（成员也是String[]，String[0]表示hidden的name，String[1]表示hidden的value）
    private List<String[]> hiddenText;

    // 提示页面打开时,须执行的js脚本
    private String javaScript;

    // 页面是否在iframe中
    private boolean inIframe;

    private String businessValue;

    //add 20170220
    private String processedId;  //公文列表已处理提示的id

    //add 20170222 
    private Integer wordType;
    private Integer userType;
    
	public PromptMessageDto() {

    }

    public PromptMessageDto(boolean operateSuccess, String message) {
        this.operateSuccess = operateSuccess;
        if (operateSuccess) {
            this.promptMessage = message;
        } else {
            this.errorMessage = message;
        }
    }

    public boolean getOperateSuccess() {
        return operateSuccess;
    }

    public void setOperateSuccess(boolean operateSuccess) {
        this.operateSuccess = operateSuccess;
    }

    public String getPromptMessage() {
        return promptMessage;
    }

    public void setPromptMessage(String promptMessage) {
        this.promptMessage = promptMessage;
    }

    public Map<String, List<String>> getFieldErrorMap() {
        return fieldErrorMap;
    }

    public List<String[]> getOperations() {
        return operations;
    }

    public void setOperations(List<String[]> operations) {
        this.operations = operations;
    }

    public void addFieldError(String fieldName, String errorMessage) {
        if (fieldErrorMap == null) {
            fieldErrorMap = new LinkedHashMap<String, List<String>>();
        }
        List<String> thisFieldErrors = fieldErrorMap.get(fieldName);
        if (thisFieldErrors == null) {
            thisFieldErrors = new ArrayList<String>();
            fieldErrorMap.put(fieldName, thisFieldErrors);
        }

        thisFieldErrors.add(errorMessage);
    }

    public void addOperation(String[] obj) {
        if (this.operations == null) {
            this.operations = new ArrayList<String[]>();
        }
        this.operations.add(obj);
    }

    public void addHiddenText(String[] obj) {
        if (this.hiddenText == null) {
            this.hiddenText = new ArrayList<String[]>();
        }
        this.hiddenText.add(obj);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String[]> getHiddenText() {
        return hiddenText;
    }

    public void setHiddenText(List<String[]> hiddenText) {
        this.hiddenText = hiddenText;
    }

    public String getJavaScript() {
        return javaScript;
    }

    public void setJavaScript(String javaScript) {
        this.javaScript = javaScript;
    }

    public boolean isInIframe() {
        return inIframe;
    }

    public void setInIframe(boolean inIframe) {
        this.inIframe = inIframe;
    }

    public String getBusinessValue() {
        return businessValue;
    }

    public void setBusinessValue(String businessValue) {
        this.businessValue = businessValue;
    }

    public String getProcessedId() {
		return processedId;
	}

	public void setProcessedId(String processedId) {
		this.processedId = processedId;
	}

	public Integer getWordType() {
		return wordType;
	}

	public void setWordType(Integer wordType) {
		this.wordType = wordType;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
}
