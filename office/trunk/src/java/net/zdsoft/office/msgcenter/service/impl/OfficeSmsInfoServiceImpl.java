package net.zdsoft.office.msgcenter.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.msgcenter.entity.OfficeSmsDetail;
import net.zdsoft.office.msgcenter.entity.OfficeSmsInfo;
import net.zdsoft.office.msgcenter.service.OfficeSmsInfoService;
import net.zdsoft.office.msgcenter.dao.OfficeSmsDetailDao;
import net.zdsoft.office.msgcenter.dao.OfficeSmsInfoDao;
import net.zdsoft.eis.base.common.dao.UserDao;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.keel.util.Pagination;
/**
 * office_sms_info
 * @author 
 * 
 */
public class OfficeSmsInfoServiceImpl implements OfficeSmsInfoService{
	private OfficeSmsInfoDao officeSmsInfoDao;
	private OfficeSmsDetailDao officeSmsDetailDao;
	private UserDao userDao;

	@Override
	public OfficeSmsInfo save(OfficeSmsInfo officeSmsInfo){
		return officeSmsInfoDao.save(officeSmsInfo);
	}

	@Override
	public Integer delete(String[] ids){
		return officeSmsInfoDao.delete(ids);
	}

	@Override
	public Integer update(OfficeSmsInfo officeSmsInfo){
		return officeSmsInfoDao.update(officeSmsInfo);
	}

	@Override
	public OfficeSmsInfo getOfficeSmsInfoById(String id){
		OfficeSmsInfo smsInfo = officeSmsInfoDao.getOfficeSmsInfoById(id);
		if(smsInfo == null){
			return null;
		}
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date sendTime = sdf.parse(smsInfo.getSendTime());
			smsInfo.setSendTimeStr(sdf1.format(sendTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		List<OfficeSmsDetail> smsDetailList = officeSmsDetailDao.getOfficeSmsDetailsByInfoId(id);
		Set<String> userIds = new HashSet<String>();
		for(OfficeSmsDetail detail : smsDetailList){
			if(StringUtils.isNotBlank(detail.getReceiverId())){
				userIds.add(detail.getReceiverId());
			}
		}
		String successPhone = "";
		String failedPhone = "";
		String noPhone = "";
		Map<String, User> userMap = userDao.getUserMap(userIds.toArray(new String[0]));
		for(OfficeSmsDetail detail : smsDetailList){
			if(detail.getSendState() == 1){
				if(StringUtils.isNotBlank(successPhone)){
					successPhone += "，";
				}
				successPhone += detail.getPhone();
				if(StringUtils.isNotBlank(detail.getReceiverId())){
					User user = userMap.get(detail.getReceiverId());
					if(user != null){
						successPhone += "(" + user.getRealname() + ")";
					}
				}
			}
			else if(detail.getSendState() == 2){
				if(StringUtils.isNotBlank(failedPhone)){
					failedPhone += "，";
				}
				failedPhone += detail.getPhone();
				if(StringUtils.isNotBlank(detail.getReceiverId())){
					User user = userMap.get(detail.getReceiverId());
					if(user != null){
						failedPhone += "(" + user.getRealname() + ")";
					}
				}
			}
			else if(detail.getSendState() == 3){
				if(StringUtils.isNotBlank(noPhone)){
					noPhone += "，";
				}
				if(StringUtils.isNotBlank(detail.getReceiverId())){
					User user = userMap.get(detail.getReceiverId());
					if(user != null){
						noPhone += user.getRealname();
					}
				}
			}
		}
		smsInfo.setSuccessPhone(successPhone);
		smsInfo.setFailedPhone(failedPhone);
		smsInfo.setNoPhone(noPhone);
		return smsInfo;
	}

	@Override
	public Map<String, OfficeSmsInfo> getOfficeSmsInfoMapByIds(String[] ids){
		return officeSmsInfoDao.getOfficeSmsInfoMapByIds(ids);
	}

	@Override
	public List<OfficeSmsInfo> getOfficeSmsInfoList(){
		return officeSmsInfoDao.getOfficeSmsInfoList();
	}

	@Override
	public List<OfficeSmsInfo> getOfficeSmsInfoPage(Pagination page){
		return officeSmsInfoDao.getOfficeSmsInfoPage(page);
	}

	@Override
	public List<OfficeSmsInfo> getOfficeSmsInfoByUnitIdList(String unitId){
		return officeSmsInfoDao.getOfficeSmsInfoByUnitIdList(unitId);
	}

	@Override
	public List<OfficeSmsInfo> getOfficeSmsInfoByUnitIdPage(String unitId, Pagination page){
		return officeSmsInfoDao.getOfficeSmsInfoByUnitIdPage(unitId, page);
	}
	
	@Override
	public List<OfficeSmsInfo> getOfficeSmsInfoByConditions(String unitId, String userId, 
			String searchBeginTime, String searchEndTime, Pagination page){
		return officeSmsInfoDao.getOfficeSmsInfoByConditions(unitId, userId, searchBeginTime, searchEndTime, page);
	}
	
	@Override
	public void batchDelete(String[] ids){
		officeSmsInfoDao.delete(ids);
		officeSmsDetailDao.deleteByInfoIds(ids);
	}
			
	public void setOfficeSmsInfoDao(OfficeSmsInfoDao officeSmsInfoDao){
		this.officeSmsInfoDao = officeSmsInfoDao;
	}

	public void setOfficeSmsDetailDao(OfficeSmsDetailDao officeSmsDetailDao) {
		this.officeSmsDetailDao = officeSmsDetailDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
