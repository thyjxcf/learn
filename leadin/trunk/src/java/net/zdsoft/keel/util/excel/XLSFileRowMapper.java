/* 
 * @(#)XLSFileRowMapper.java    Created on 2007-3-21
 * Copyright (c) 2007 ZDSoft Networks, Inc. All rights reserved.
 * $Id: XLSFileRowMapper.java,v 1.1 2007/04/28 03:46:25 huangwj Exp $
 */
package net.zdsoft.keel.util.excel;

import java.util.Map;

/**
 * 处理XLS文件中每行记录和对象映射的接口类.
 * 
 * @author huangwj
 * @version $Revision: 1.1 $, $Date: 2007/04/28 03:46:25 $
 */
public interface XLSFileRowMapper<T> {

    /**
     * 将文件中的某行记录映射到某个对象.
     * 
     * @param rs
     *            对应于某行记录的结果集, key为字段的标识, value为字段的值
     * @return 记录所对应的对象, 如可以根据结果集来构建一个实体对象.
     */
    T mapRow(Map<String, Object> rs);

}
