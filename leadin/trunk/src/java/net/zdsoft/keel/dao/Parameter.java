/* 
 * @(#)Parameter.java    Created on 2007-1-27
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/dao/Parameter.java,v 1.1 2007/04/13 02:50:27 liangxiao Exp $
 */
package net.zdsoft.keel.dao;

/**
 * jdbc参数，包括参数值和参数类型
 * 
 * @author liangxiao
 * @version $Revision: 1.1 $, $Date: 2007/04/13 02:50:27 $
 */
public class Parameter {

    private Object arg;
    private int argType;

    /**
     * 构造方法
     */
    public Parameter() {
    }

    /**
     * 构造方法
     * 
     * @param arg
     *            参数值
     * @param argType
     *            参数类型
     */
    public Parameter(Object arg, int argType) {
        this.arg = arg;
        this.argType = argType;
    }

    /**
     * 取得参数值
     * 
     * @return 参数值
     */
    public Object getArg() {
        return arg;
    }

    /**
     * 设置参数值
     * 
     * @param arg
     *            参数值
     */
    public void setArg(Object arg) {
        this.arg = arg;
    }

    /**
     * 取得参数类型
     * 
     * @return 参数类型
     */
    public int getArgType() {
        return argType;
    }

    /**
     * 设置参数类型
     * 
     * @param argType
     *            参数类型
     */
    public void setArgType(int argType) {
        this.argType = argType;
    }

}
