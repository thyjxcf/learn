/* 
 * @(#)Parameters.java    Created on 2007-1-27
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/dao/Parameters.java,v 1.1 2007/04/13 02:50:27 liangxiao Exp $
 */
package net.zdsoft.keel.dao;

import java.util.ArrayList;

/**
 * jdbc参数集合
 * 
 * @author liangxiao
 * @version $Revision: 1.1 $, $Date: 2007/04/13 02:50:27 $
 */
public class Parameters {

    private ArrayList<Parameter> parameters;

    /**
     * 构造方法
     */
    public Parameters() {
        parameters = new ArrayList<Parameter>();
    }

    /**
     * 增加参数
     * 
     * @param parameter
     *            参数
     */
    public void add(Parameter parameter) {
        parameters.add(parameter);
    }

    /**
     * 增加参数
     * 
     * @param arg
     *            参数值
     * @param argType
     *            参数类型
     */
    public void add(Object arg, int argType) {
        parameters.add(new Parameter(arg, argType));
    }

    /**
     * 清除参数
     */
    public void clear() {
        parameters.clear();
    }

    /**
     * 取得参数值数组
     * 
     * @return 参数值数组
     */
    public Object[] getArgs() {
        Object[] args = new Object[parameters.size()];
        for (int i = 0; i < parameters.size(); i++) {
            args[i] = ((Parameter) parameters.get(i)).getArg();
        }
        return args;
    }

    /**
     * 取得参数类型数组
     * 
     * @return 取得参数类型数组
     */
    public int[] getArgTypes() {
        int[] argTypes = new int[parameters.size()];
        for (int i = 0; i < parameters.size(); i++) {
            argTypes[i] = ((Parameter) parameters.get(i)).getArgType();
        }
        return argTypes;
    }

}
