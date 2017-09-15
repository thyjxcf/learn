package net.zdsoft.leadin.common.dao;

import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;

public interface SystemCommonDao {
    /**
     * 返加ArrayList结果集
     * 
     * @param sql 要执行的SQL语句
     * @param columnnum 查询字符串列总数
     * @return
     */
    public List<String[]> getArrayList(String sql, int columnnum);
    
    /**
     * 根据sql取信息
     * @param sql
     * @return
     */
    public List<String[]> getArrayList(String sql) throws Exception;

    /**
     * 取列表信息
     * @param sql 语句
     * @param columnCount 列数
     * @param page 分页对象
     * @return
     */
    public List<String[]> getArrayList(String sql, int columnCount,
            Pagination page);

    /**
     * 根据sql取信息
     * @param sql
     * @return
     */
    public Map<String,String> getHashMap(String sql) throws Exception;

    /**
     * 执行无结果型sql语句
     * 
     * @param sql 要执行的SQL语句
     */
    public void commonExec(String sql);

    /**
     * 取回此SQL语语句执行结果集中的第一条记录
     * 
     * @param sql 要执行的SQL语句
     * @return
     */
    public String getOneRecord(String sql);

    /**
     * 批量更新sql语句
     * 
     * @param sqls 要执行的SQL语句
     * @return
     */
    public int batchUpdate(String[] sqls);
    
    public int batchUpdateBySql(String sql, List<Object[]> args, int[] types);
    
    /**
     * 更新数据
     * @param sql
     * @param args
     * @return
     */
    public int updateSQL(String sql, Object... args); 
    
    /**
     * 更新数据
     * @param sql
     * @param prefixArgs
     * @param inArgs
     * @return
     */
    public int updateSQLForIn(String sql, Object[] prefixArgs, Object[] inArgs); 
}
