package net.zdsoft.office.customer.dao;

import java.util.*;

import net.zdsoft.office.customer.entity.OfficeCustomerFollowRecord;
import net.zdsoft.keel.util.Pagination;

/**
 * office_customer_follow_record 
 * @author 
 * 
 */
public interface OfficeCustomerFollowRecordDao {
	/**
	 * 新增office_customer_follow_record
	 * @param officeCustomerFollowRecord
	 * @return"
	 */
	public OfficeCustomerFollowRecord save(OfficeCustomerFollowRecord officeCustomerFollowRecord);
	
	/**
	 * 根据ids数组删除office_customer_follow_record
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);
	
	public List<OfficeCustomerFollowRecord> getFollowerRecordApplyId(String userId);
	/**
	 * 更新office_customer_follow_record
	 * @param officeCustomerFollowRecord
	 * @return
	 */
	public Integer update(OfficeCustomerFollowRecord officeCustomerFollowRecord);
	
	public Integer updateApplyId(String newApplyId,String everApplyId);
	/**
	 * 根据id获取office_customer_follow_record;
	 * @param id);
	 * @return
	 */
	public OfficeCustomerFollowRecord getOfficeCustomerFollowRecordById(String id);
		
	/**
	 * 根据ids数组查询office_customer_follow_recordmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeCustomerFollowRecord> getOfficeCustomerFollowRecordMapByIds(String[] ids);
	
	/**
	 * 获取office_customer_follow_record列表
	 * @return
	 */
	public List<OfficeCustomerFollowRecord> getOfficeCustomerFollowRecordList(String applyId);
		
	/**
	 * 分页获取office_customer_follow_record列表
	 * @param page
	 * @return
	 */
	public List<OfficeCustomerFollowRecord> getOfficeCustomerFollowRecordPage(Pagination page);
	
}
