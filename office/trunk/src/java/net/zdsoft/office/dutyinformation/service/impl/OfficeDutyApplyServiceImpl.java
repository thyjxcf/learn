package net.zdsoft.office.dutyinformation.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyApply;
import net.zdsoft.office.dutyinformation.entity.OfficeDutyInformationSet;
import net.zdsoft.office.dutyinformation.entity.OfficeDutyInformationSetEx;
import net.zdsoft.office.dutyinformation.service.OfficeDutyApplyService;
import net.zdsoft.office.dutyinformation.service.OfficeDutyInformationSetExService;
import net.zdsoft.office.dutyinformation.service.OfficeDutyInformationSetService;
import net.zdsoft.office.dutyinformation.dao.OfficeDutyApplyDao;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_apply
 * @author 
 * 
 */
public class OfficeDutyApplyServiceImpl implements OfficeDutyApplyService{
	private OfficeDutyApplyDao officeDutyApplyDao;
	private OfficeDutyInformationSetExService officeDutyInformationSetExService;
	private UserService userService;
	private TeacherService teacherService;

	@Override
	public OfficeDutyApply save(OfficeDutyApply officeDutyApply){
		return officeDutyApplyDao.save(officeDutyApply);
	}

	@Override
	public Integer delete(String[] ids){
		return officeDutyApplyDao.delete(ids);
	}

	@Override
	public Integer update(OfficeDutyApply officeDutyApply){
		return officeDutyApplyDao.update(officeDutyApply);
	}

	@Override
	public OfficeDutyApply getOfficeDutyApplyById(String id){
		return officeDutyApplyDao.getOfficeDutyApplyById(id);
	}

	@Override
	public Map<String, OfficeDutyApply> getOfficeDutyApplyMapByIds(String[] ids){
		return officeDutyApplyDao.getOfficeDutyApplyMapByIds(ids);
	}

	@Override
	public List<OfficeDutyApply> getOfficeDutyApplyList(){
		return officeDutyApplyDao.getOfficeDutyApplyList();
	}

	@Override
	public List<OfficeDutyApply> getOfficeDutyApplyPage(Pagination page){
		return officeDutyApplyDao.getOfficeDutyApplyPage(page);
	}

	@Override
	public List<OfficeDutyApply> getOfficeDutyApplyByUnitIdList(String unitId){
		return officeDutyApplyDao.getOfficeDutyApplyByUnitIdList(unitId);
	}

	@Override
	public List<OfficeDutyApply> getOfficeDutyApplyByUnitIdPage(String unitId, Pagination page){
		return officeDutyApplyDao.getOfficeDutyApplyByUnitIdPage(unitId, page);
	}

	@Override
	public Map<String, OfficeDutyApply> getOfficeDutyAppliesMap(String unitId,
			String dutyId) {
		Map<String,OfficeDutyApply> applyMap=new HashMap<String, OfficeDutyApply>();
		List<OfficeDutyApply> officeDutyApplies=officeDutyApplyDao.getOfficeDutyAppliesMap(unitId, dutyId);
		
		Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		Map<String,Teacher> teacherMap=new HashMap<String, Teacher>();
		Set<String> teacherSet=new HashSet<String>();
		if(CollectionUtils.isNotEmpty(officeDutyApplies)){
			for (OfficeDutyApply officeDutyApply : officeDutyApplies) {
				if(userMap.containsKey(officeDutyApply.getUserId())){
					User user=userMap.get(officeDutyApply.getUserId());
					if(user!=null){
						teacherSet.add(user.getTeacherid());
					}
				}
			}
			
			teacherMap=teacherService.getTeacherMap(teacherSet.toArray(new String[0]));
			for (OfficeDutyApply officeDutyApply : officeDutyApplies) {
				if(userMap.containsKey(officeDutyApply.getUserId())){
					User user=userMap.get(officeDutyApply.getUserId());
					if(user!=null){
						Teacher teacher=teacherMap.get(user.getTeacherid());
						if(teacher!=null&&StringUtils.isNotBlank(teacher.getPersonTel())){
							officeDutyApply.setUserName(user.getRealname()+"("+teacher.getPersonTel()+")");
						}else{
							officeDutyApply.setUserName(user.getRealname());
						}
					}
				}
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
				applyMap.put(sdf.format(officeDutyApply.getApplyDate())+"_"+officeDutyApply.getType(), officeDutyApply);
			}
		}
		return applyMap;
	}

	@Override
	public boolean saveOfficeDutyApplyInfo(String[] applyRooms, String dutyId,
			String unitId,String userId, boolean admin) {
		if(ArrayUtils.isNotEmpty(applyRooms)){
			Arrays.sort(applyRooms);
			boolean flag = false;//判断同一时间是否有其他人申请了，那边要刷新页面重新申请
			boolean flag2 = false;//判断同一时间是否有其他人申请了，那边要刷新页面重新申请
			List<OfficeDutyApply> officeDutyApplies=new ArrayList<OfficeDutyApply>();
			
			Set<String> userSet=new HashSet<String>();//与人设一致
			
			Set<String>  deleteUser=new HashSet<String>();//删除数据
			
			for (String apply : applyRooms) {
				String[] idPeriod = apply.split("_");
				if(!admin&&!StringUtils.equals(userId, idPeriod[2])){
					continue;
				}
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
				Date date=null;
				try {
					date = sdf.parse(idPeriod[0]);
					if(!admin){
						flag = officeDutyApplyDao.isApplyByOthers(unitId,dutyId,idPeriod[1],idPeriod[2],date);
					}
					flag2 = officeDutyApplyDao.isApplyAdmin(unitId,dutyId,idPeriod[2],date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(flag||flag2){
					break;
				}
				
				if(admin){
					userSet.add(idPeriod[2]);
				}
				
				////先删除原先报名信息
				deleteUser.add(idPeriod[2]);
				//if(!admin){
				//	officeDutyApplyDao.deleteApplyInfo(unitId, dutyId, idPeriod[2]);
				//}else{
				//	officeDutyApplyDao.deleteApplyInfo(unitId, dutyId, idPeriod[2]);
				//}
				
				OfficeDutyApply officeDutyApply=new OfficeDutyApply();
				officeDutyApply.setUnitId(unitId);
				officeDutyApply.setApplyDate(date);
				officeDutyApply.setDutyInformationId(dutyId);
				officeDutyApply.setType(idPeriod[1]);
				officeDutyApply.setUserId(idPeriod[2]);
				officeDutyApplies.add(officeDutyApply);
			}
			
			if(flag||flag2){
				boolean des=flag||flag2;
				return des;
			}
			
			//删除数据
			if(admin){
				officeDutyApplyDao.deleteApplyInfo(unitId, dutyId, null);
			}else{
				officeDutyApplyDao.deleteApplyInfo(unitId, dutyId, deleteUser.toArray(new String[0]));
			}
			
			List<OfficeDutyInformationSetEx> officeDutyInformationSetExs=new ArrayList<OfficeDutyInformationSetEx>();
			if(admin){
				Set<String> oldUserSet=new HashSet<String>();
				List<OfficeDutyInformationSetEx> officeDutyInformationSetExs2=officeDutyInformationSetExService.getOfficeDutyInformationSetExsByDutyId(dutyId);
				for (OfficeDutyInformationSetEx officeDutyInformationSetEx : officeDutyInformationSetExs2) {
					oldUserSet.add(officeDutyInformationSetEx.getUserId());
				}
				for (String string : userSet) {
					for (String old : oldUserSet) {
						if(!StringUtils.equals(string, old)){
							OfficeDutyInformationSetEx officeD=new OfficeDutyInformationSetEx();
							officeD.setDutyInformationId(dutyId);
							officeD.setUserId(string);
							officeDutyInformationSetExs.add(officeD);
							break;
						}
					}
				}
				//officeDutyInformationSetExService.delete(dutyId);
			}
			if(admin&&CollectionUtils.isNotEmpty(officeDutyInformationSetExs)){
				officeDutyInformationSetExService.batchSave(officeDutyInformationSetExs);
			}
			
			officeDutyApplyDao.batchSaveApplyInfo(officeDutyApplies);
			
		}else{
			 if(admin){
				 officeDutyApplyDao.deleteApplyInfo(unitId, dutyId, null);
			 }else{
				 officeDutyApplyDao.deleteApplyInfo(unitId, dutyId, new String[]{userId});
			 }
		}
		return false;
	}

	@Override
	public List<OfficeDutyApply> getOfficeDutyAppliesByUnitIdAndUserId(
			String unitId, Date applyDate, String userId) {
		List<OfficeDutyApply> officeDutyApplies=officeDutyApplyDao.getOfficeDutyAppliesByUnitIdAndUserId(unitId, applyDate, userId);
		List<OfficeDutyApply> desDutyApplies=new ArrayList<OfficeDutyApply>();
		Set<String> userIdSet=new HashSet<String>();
		for (OfficeDutyApply officeDutyApply : officeDutyApplies) {
			userIdSet.add(officeDutyApply.getUserId());
		}
		Map<String,User> userMap=userService.getUsersMap(userIdSet.toArray(new String[0]));
		for (String string : userIdSet) {
			for (OfficeDutyApply officeDutyApply : officeDutyApplies) {
				if(StringUtils.equals(string, officeDutyApply.getUserId())&&userMap.containsKey(officeDutyApply.getUserId())){
					User user=userMap.get(officeDutyApply.getUserId());
					if(user!=null){
						officeDutyApply.setUserName(user.getRealname());
						OfficeDutyApply officeDutyApply2=new OfficeDutyApply();
						officeDutyApply2.setUserId(officeDutyApply.getUserId());
						officeDutyApply2.setUserName(user.getRealname());
						officeDutyApply2.setApplyDate(officeDutyApply.getApplyDate());
						officeDutyApply2.setDutyInformationId(officeDutyApply.getDutyInformationId());
						officeDutyApply2.setId(officeDutyApply.getId());
						officeDutyApply2.setUnitId(officeDutyApply.getUnitId());
						desDutyApplies.add(officeDutyApply2);
					}
					break;
				}
			}
		}
		return desDutyApplies;
	}

	public void setOfficeDutyApplyDao(OfficeDutyApplyDao officeDutyApplyDao){
		this.officeDutyApplyDao = officeDutyApplyDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setOfficeDutyInformationSetExService(
			OfficeDutyInformationSetExService officeDutyInformationSetExService) {
		this.officeDutyInformationSetExService = officeDutyInformationSetExService;
	}

}

	