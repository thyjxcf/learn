/* 
 * @(#)MessageSupport.java    Created on 2004-9-8
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/action/MessageSupport.java,v 1.9 2007/04/13 02:50:27 liangxiao Exp $
 */
package net.zdsoft.keel.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息回显机制的默认实现类
 * 
 * @author liangxiao
 * @version $Revision: 1.9 $, $Date: 2007/04/13 02:50:27 $
 */
public class MessageSupport implements Messageable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3257288036961498163L;
    
    protected static final String KEY = "KEEL.MessageSupport";

    private Collection<String> actionErrors = null;
    private Collection<String> actionMessages = null;
    private Map<String, List<String>> fieldErrors = null;

    /**
     * 构造方法
     */
    public MessageSupport() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#setActionErrors(java.util.Collection)
     */
    public synchronized void setActionErrors(Collection<String> errorMessages) {
        this.actionErrors = errorMessages;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#getActionErrors()
     */
    public synchronized Collection<String> getActionErrors() {
        return new ArrayList<String>(internalGetActionErrors());
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#setActionMessages(java.util.Collection)
     */
    public synchronized void setActionMessages(Collection<String> messages) {
        this.actionMessages = messages;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#getActionMessages()
     */
    public synchronized Collection<String> getActionMessages() {
        return new ArrayList<String>(internalGetActionMessages());
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#setFieldErrors(java.util.Map)
     */
    public synchronized void setFieldErrors(Map<String, List<String>> errorMap) {
        this.fieldErrors = errorMap;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#getFieldErrors()
     */
    public synchronized Map<String, List<String>> getFieldErrors() {
        return internalGetFieldErrors();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#addActionError(java.lang.String)
     */
    public synchronized void addActionError(String anErrorMessage) {
        internalGetActionErrors().add(anErrorMessage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#addActionMessage(java.lang.String)
     */
    public synchronized void addActionMessage(String aMessage) {
        internalGetActionMessages().add(aMessage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#addFieldError(java.lang.String,
     *      java.lang.String)
     */
    public synchronized void addFieldError(String fieldName, String errorMessage) {
        final Map<String, List<String>> errors = internalGetFieldErrors();
        List<String> thisFieldErrors = errors.get(fieldName);

        if (thisFieldErrors == null) {
            thisFieldErrors = new ArrayList<String>();
            errors.put(fieldName, thisFieldErrors);
        }

        thisFieldErrors.add(errorMessage);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#hasActionErrors()
     */
    public synchronized boolean hasActionErrors() {
        return (actionErrors != null) && !actionErrors.isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#hasActionMessages()
     */
    public synchronized boolean hasActionMessages() {
        return (actionMessages != null) && !actionMessages.isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#hasErrors()
     */
    public synchronized boolean hasErrors() {
        return (hasActionErrors() || hasFieldErrors());
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.zdsoft.keel.action.Messageable#hasFieldErrors()
     */
    public synchronized boolean hasFieldErrors() {
        return (fieldErrors != null) && !fieldErrors.isEmpty();
    }

    private Collection<String> internalGetActionErrors() {
        if (actionErrors == null) {
            actionErrors = new ArrayList<String>();
        }

        return actionErrors;
    }

    private Collection<String> internalGetActionMessages() {
        if (actionMessages == null) {
            actionMessages = new ArrayList<String>();
        }

        return actionMessages;
    }

    private Map<String, List<String>> internalGetFieldErrors() {
        if (fieldErrors == null) {
            // 为了使validate字段信息提示按配置文件中定义的顺序显示,
            // 所以使用了LinkedHashMap
            fieldErrors = new LinkedHashMap<String, List<String>>();
        }

        return fieldErrors;
    }

}
