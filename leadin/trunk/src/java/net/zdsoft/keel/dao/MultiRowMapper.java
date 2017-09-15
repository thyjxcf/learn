/*
 * Created on 2004-12-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.zdsoft.keel.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 这是用来处理多行记录集的情况的接口
 * 
 * @author liangxiao
 * @version $Revision: 1.4 $, $Date: 2007/01/11 09:15:14 $
 */
public interface MultiRowMapper<T> {

    /**
     * 把记录集的一行记录映射成一个对象，方法里不需要执行rs.next
     * 
     * @param rs
     *            记录集
     * @param rowNum
     *            这是第几条记录，从0开始
     * @return 对象
     * @throws SQLException
     */
    T mapRow(ResultSet rs, int rowNum) throws SQLException;

}
