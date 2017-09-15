package net.zdsoft.office.desktop.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.desktop.entity.Memo;

public interface MemoDAO {

    /**
     * 插入新备忘录
     * 
     * @param memo
     */
    void insertMemo(Memo memo);

    void insertMemos(Memo... memos);

    /**
     * 删除备忘录
     * 
     * @param ids
     */
    void deleteMemos(String[] ids);

    /**
     * 根据ID获取备忘录
     * 
     * @param id
     *            备忘录ID
     * @return
     */
    Memo findMemo(String id);

    /**
     * 修改
     * 
     * @param memo
     */
    void updateMemo(Memo memo);

    /**
     * 获取用户备忘录列表
     * 
     * @param userId
     *            用户ID
     * @param page
     * @return
     */
    List<Memo> findMemos(String userId, Pagination page);

    /**
     * 获取用户备忘录列表
     * 
     * @param userId
     *            用户ID
     * @param page
     * @return
     */
    List<Memo> findMemosByTime(String userId, Date startDate, Date endDate, Pagination page, boolean isDesc);

    /**
     * 查询今天以前的记录倒序
     * 
     * @param userId
     * @param count
     * @return
     */
    List<Memo> findMemosByDayDesc(String userId, String[] dateStrs, Pagination page);

    /**
     * 查询今天以后的记录顺序
     * 
     * @param userId
     * @param count
     * @return
     */
    List<Memo> findMemosByDayAsc(String userId, String[] dateStrs, Pagination page);

    /**
     * 查询指定日期内，备忘信息的记录条数
     * 
     * @param dates
     * @return
     */
    Map<String, Integer> findMenosCount(String userId, String[] dateStrs);

    Map<String, Integer> findMenosCount(String userId, Date startDate, Date endDate);

    int findMemoCount(String userId, Date date);
    
    int findTotalMemoCountByDate(String userId, Date date);
    
    /**
     * 获取未到日期的备忘信息list
     * @param userId
     * @return
     */
    public List<Memo> getMemoListByUserId(String userId);
}
