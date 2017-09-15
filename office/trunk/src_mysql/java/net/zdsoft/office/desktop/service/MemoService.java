package net.zdsoft.office.desktop.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.desktop.entity.Memo;

/**
 * @author zhuhf
 * @version $Revision: 1.0 $, $Date: 2009-3-13 下午02:27:19 $
 */
public interface MemoService {

    /**
     * 添加新备忘录
     * 
     * @param memo
     */
    void addMemo(Memo memo, String userId);

    void addMemos(Memo... memos);

    /**
     * 修改新备忘录
     * 
     * @param memo
     */
    void modifyMemo(Memo memo);

    /**
     * 删除备忘录
     * 
     * @param ids
     */
    void removeMemos(String[] ids);

    /**
     * 根据ID获取备忘录
     * 
     * @param id
     *            备忘录ID
     * @return
     */
    Memo getMemo(String id);

    /**
     * 获取用户备忘录列表
     * 
     * @param userId
     *            用户ID
     * @param page
     * @return
     */
    List<Memo> getMemos(String userId, Pagination page);

    /**
     * 获取用户备忘录列表
     * 
     * @param userId
     *            用户ID
     * @param page
     * @return
     */
    List<Memo> getMemosByTime(String userId, Date startDate, Date endDate, Pagination page, boolean isDesc);

    /**
     * 查询指定日期下备忘录的数量，并以map的形式返回
     * 
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    Map<String, Integer> getMenoCount(String userId, Date startDate, Date endDate);

    /**
     * 指定日期内备忘记录的数量
     * 
     * @param userId
     * @param date
     * @return
     */
    int getMemoCount(String userId, Date date);
    
    /**
     * 统计还没进行的备忘录总条数
     * @param userId
     * @param date
     * @return
     */
    int findTotalMemoCountByDate(String userId, Date date);

    List<Memo> getMemosByCurentDay(String userId, int count);

    List<Memo> getMemosByDayDesc(String userId, String[] dateStrs, Pagination page);

    List<Memo> getMemosByDayAsc(String userId, String[] dateStrs, Pagination page);
    /**
     * 获得指定时间段内的备忘记录
     * @param userId
     * @param weekDate
     * @return
     */
	List<List<Memo>> getWeekMemos(String userId, List<Date> weekDate);
	
	/**
     * 获取未到日期的备忘信息list
     * @param userId
     * @return
     */
    public List<Memo> getMemoListByUserId(String userId);

}
