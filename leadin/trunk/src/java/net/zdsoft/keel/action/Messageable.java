/*
 * Created on 2004-9-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.keel.action;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 消息回显机制的支持，其中actionError表示针对整个Action的错误，
 * actionMessage表示针对整个Action的信息，fieldError表示针对某个表单域的错误
 * 
 * @author liangxiao
 * @version $Revision: 1.4 $, $Date: 2007/01/11 03:59:32 $
 */
public interface Messageable extends Serializable {

    /**
     * 设置actionErrors
     * 
     * @param errorMessages
     */
    public void setActionErrors(Collection<String> errorMessages);

    /**
     * 取得actionErrors
     * 
     * @return
     */
    public Collection<String> getActionErrors();

    /**
     * 设置actionMessages
     * 
     * @param messages
     */
    public void setActionMessages(Collection<String> messages);

    /**
     * 取得actionMessages
     * 
     * @return
     */
    public Collection<String> getActionMessages();

    /**
     * 设置fieldErrors
     * 
     * @param errorMap
     */
    public void setFieldErrors(Map<String, List<String>> errorMap);

    /**
     * 取得fieldErrors
     * 
     * @return
     */
    public Map<String, List<String>> getFieldErrors();

    /**
     * 增加actionError
     * 
     * @param anErrorMessage
     */
    public void addActionError(String anErrorMessage);

    /**
     * 增加actionMessage
     * 
     * @param aMessage
     */
    public void addActionMessage(String aMessage);

    /**
     * 增加fieldError
     * 
     * @param fieldName
     * @param errorMessage
     */
    public void addFieldError(String fieldName, String errorMessage);

    /**
     * 判断是否有actionError
     * 
     * @return
     */
    public boolean hasActionErrors();

    /**
     * 判断是否有actionMessage
     * 
     * @return
     */
    public boolean hasActionMessages();

    /**
     * 判断是否有错误，包括actionError和fieldError
     * 
     * @return
     */
    public boolean hasErrors();

    /**
     * 判断是否有fieldError
     * 
     * @return
     */
    public boolean hasFieldErrors();
}