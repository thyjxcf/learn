package net.zdsoft.office.secretaryMail.service;

import java.util.*;
import net.zdsoft.office.secretaryMail.entity.OfficeJzmail;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;
/**
 * office_jzmail
 * @author 
 * 
 */
public interface OfficeJzmailService{

	/**
	 * 新增office_jzmail
	 * @param officeJzmail
	 * @return
	 */
	public OfficeJzmail save(OfficeJzmail officeJzmail);
	
	/**
	 * 新增office_jzmail
	 * @param officeJzmail
	 * @return
	 */
	public void add(OfficeJzmail officeJzmail, List<UploadFile> uploadFileList);

	/**
	 * 根据ids数组删除office_jzmail数据
	 * @param ids
	 * @return
	 */
	public Integer delete(String[] ids);

	/**
	 * 更新office_jzmail
	 * @param officeJzmail
	 * @return
	 */
	public Integer update(OfficeJzmail officeJzmail);

	/**
	 * 根据id获取office_jzmail
	 * @param id
	 * @return
	 */
	public OfficeJzmail getOfficeJzmailById(String id);

	/**
	 * 根据ids数组查询office_jzmailmap
	 * @param ids
	 * @return
	 */
	public Map<String, OfficeJzmail> getOfficeJzmailMapByIds(String[] ids);

	/**
	 * 获取office_jzmail列表
	 * @return
	 */
	public List<OfficeJzmail> getOfficeJzmailList();

	/**
	 * 分页获取office_jzmail列表
	 * @param page
	 * @return
	 */
	public List<OfficeJzmail> getOfficeJzmailPage(Pagination page);

	/**
	 * 根据UnitId获取office_jzmail列表
	 * @param unitId
	 * @return
	 */
	public List<OfficeJzmail> getOfficeJzmailByUnitIdList(String unitId);

	/**
	 * 根据UnitId分页获取office_jzmail
	 * @param unitId
	 * @param page
	 * @return
	 */
	public List<OfficeJzmail> getOfficeJzmailByUnitIdPage(String unitId, Pagination page);
	
	/**
	 * 根据unitId和其他条件查询我的信箱
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param title
	 * @param page
	 * @return
	 */
	public List<OfficeJzmail> getOfficeJzmailByUnitIdAndTitle(String unitId,String userId,String startTime,String endTime,String title,Pagination page);
	
	/**
	 * 根据unitId和其他条件查询我的信箱
	 * @param unitId
	 * @param startTime
	 * @param endTime
	 * @param title
	 * @param page
	 * @return
	 */
	public List<OfficeJzmail> getOfficeJzmailByUnitIdAndTitleAndOther(String startTime,String endTime,String title,String createUserName,String unitName,String state,Pagination page);
}