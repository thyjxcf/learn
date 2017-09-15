package net.zdsoft.office.boardroom.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.office.boardroom.dao.OfficeApplyDetailsXjDao;
import net.zdsoft.office.boardroom.entity.OfficeApplyDetailsXj;
import net.zdsoft.office.boardroom.entity.OfficeApplyExXj;
import net.zdsoft.office.boardroom.entity.OfficeBoardroomApplyXj;
import net.zdsoft.office.boardroom.entity.OfficeBoardroomXj;
import net.zdsoft.office.boardroom.service.OfficeApplyDetailsXjService;
import net.zdsoft.office.boardroom.service.OfficeApplyExXjService;
import net.zdsoft.office.boardroom.service.OfficeBoardroomApplyXjService;
import net.zdsoft.office.boardroom.service.OfficeBoardroomXjService;
import net.zdsoft.office.util.Constants;
/**
 * office_apply_details_xj
 * @author 
 * 
 */
public class OfficeApplyDetailsXjServiceImpl implements OfficeApplyDetailsXjService{
	private OfficeApplyDetailsXjDao officeApplyDetailsXjDao;
	private UserService userService;
	private OfficeBoardroomXjService officeBoardroomXjService;
	private OfficeBoardroomApplyXjService officeBoardroomApplyXjService;
	private OfficeApplyExXjService officeApplyExXjService;

	@Override
	public OfficeApplyDetailsXj save(OfficeApplyDetailsXj officeApplyDetailsXj){
		return officeApplyDetailsXjDao.save(officeApplyDetailsXj);
	}

	@Override
	public Integer delete(String[] ids){
		return officeApplyDetailsXjDao.delete(ids);
	}

	@Override
	public Integer update(OfficeApplyDetailsXj officeApplyDetailsXj){
		return officeApplyDetailsXjDao.update(officeApplyDetailsXj);
	}

	@Override
	public OfficeApplyDetailsXj getOfficeApplyDetailsXjById(String id){
		return officeApplyDetailsXjDao.getOfficeApplyDetailsXjById(id);
	}

	@Override
	public Map<String, OfficeApplyDetailsXj> getOfficeApplyDetailsXjMapByIds(String[] ids){
		return officeApplyDetailsXjDao.getOfficeApplyDetailsXjMapByIds(ids);
	}

	@Override
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjList(){
		return officeApplyDetailsXjDao.getOfficeApplyDetailsXjList();
	}

	@Override
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjPage(Pagination page){
		return officeApplyDetailsXjDao.getOfficeApplyDetailsXjPage(page);
	}

	@Override
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjByUnitIdList(String unitId){
		return officeApplyDetailsXjDao.getOfficeApplyDetailsXjByUnitIdList(unitId);
	}

	@Override
	public List<OfficeApplyDetailsXj> getOfficeApplyDetailsXjByUnitIdPage(String unitId, Pagination page){
		return officeApplyDetailsXjDao.getOfficeApplyDetailsXjByUnitIdPage(unitId, page);
	}

	@Override
	public Map<String, OfficeApplyDetailsXj> getApplyMap(String roomId,
			Date applyStartDate, Date applyEndDate, String unitId,String deptId) {
		Map<String,	OfficeApplyDetailsXj> applyMap = new HashMap<String, OfficeApplyDetailsXj>();
		
		List<OfficeApplyDetailsXj> officeApplyDetailsXjList = officeApplyDetailsXjDao.getOfficeApplyDetailsXjByRoomId(roomId, applyStartDate, applyEndDate, unitId);
		
		
		//Map<String, User> userMap = userService.getUserWithDelMap(unitId);
		
		for(OfficeApplyDetailsXj officeApplyDetailsXj:officeApplyDetailsXjList){
			//如果不是当前登录人员，那么要显示姓名
			//if(!userId.equals(officeApplyDetailsXj.getUserId())){
			//	if(userMap.get(officeApplyDetailsXj.getUserId())!=null){
			//		officeApplyDetailsXj.setUserName(userMap.get(officeApplyDetailsXj.getUserId()).getRealname());
			//	}else{
			//		officeApplyDetailsXj.setUserName("用户已删除");
			//	}
			//}
			SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd");
			applyMap.put(sdf4.format(officeApplyDetailsXj.getApplyDate())+"_"+officeApplyDetailsXj.getApplyPeriod(), officeApplyDetailsXj);
		}
		return applyMap;
	}
	
	@Override
	public boolean saveRoom(OfficeApplyDetailsXj officeDetailsXj) {//TODO
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
		String[] applyRooms = officeDetailsXj.getApplyTimes();
		Arrays.sort(applyRooms);  //进行排序
		boolean flag = false;//判断同一时间是否有其他人申请了，那边要刷新页面重新申请
		String id = "";//申请日期id
		String periods = "";//同一个会议室的节次拼接
		String content = "";//使用时间说明
		List<OfficeApplyExXj> officeApplyExXjs = new ArrayList<OfficeApplyExXj>();
		List<OfficeApplyDetailsXj> officeApplyDetailsXjs = new ArrayList<OfficeApplyDetailsXj>();
		List<OfficeBoardroomApplyXj> officeBoardroomApplyXjs = new ArrayList<OfficeBoardroomApplyXj>();
		Map<String, OfficeBoardroomApplyXj> officeBoardroomApplyXjMap = new HashMap<String, OfficeBoardroomApplyXj>();
		
		OfficeBoardroomXj officeBoardroomXj = officeBoardroomXjService.getOfficeBoardroomXjById(officeDetailsXj.getRoomId());
		
		//申请时段MAP
		Map<String, String> applyDateMap = new HashMap<String, String>();
		for(String applyRoom:applyRooms){
			String[] idPeriod = applyRoom.split("_");
			String item = applyDateMap.get(idPeriod[0]);
			if(StringUtils.isBlank(item)){
				applyDateMap.put(idPeriod[0], idPeriod[1]);
			}else{
				applyDateMap.put(idPeriod[0], item+","+idPeriod[1]);
			}
		}
		
		//设置申请记录
		for(String key : applyDateMap.keySet()){
			OfficeBoardroomApplyXj officeBoardroomApplyXj = new OfficeBoardroomApplyXj();
			officeBoardroomApplyXj.setId(UUIDGenerator.getUUID());
			officeBoardroomApplyXj.setUnitId(officeDetailsXj.getUnitId());
			officeBoardroomApplyXj.setApplyUserId(officeDetailsXj.getUserId());
			officeBoardroomApplyXj.setApplyDeptId(officeDetailsXj.getDeptId());
			officeBoardroomApplyXj.setRoomId(officeDetailsXj.getRoomId());
			try {
				officeBoardroomApplyXj.setApplyDate(sdf2.parse(key));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			officeBoardroomApplyXj.setCreateTime(new Date());
			if("0".equals(officeBoardroomXj.getNeedAudit())){//不需要审核
				officeBoardroomApplyXj.setState(String.valueOf("5"));//直接通过
				//officeBoardroomApplyXj.setAuditUserId(officeDetailsXj.getUserId());
				//officeBoardroomApplyXj.setAuditDate(new Date());
			}else{
				officeBoardroomApplyXj.setState(String.valueOf(Constants.APPLY_STATE_NEED_AUDIT));
			}
			
			officeBoardroomApplyXjs.add(officeBoardroomApplyXj);
		}
		
		for(OfficeBoardroomApplyXj item : officeBoardroomApplyXjs){
			officeBoardroomApplyXjMap.put(sdf2.format(item.getApplyDate()), item);
		}
		
		for(String key : applyDateMap.keySet()){
			String[] datePeriod = applyDateMap.get(key).split(",");
			OfficeBoardroomApplyXj officeBoardroomApplyXj = officeBoardroomApplyXjMap.get(key);
			
			for(int i = 0;i < datePeriod.length;i++){
				try {
					flag = officeApplyDetailsXjDao.isApplyByOthers(datePeriod[i],sdf2.parse(key),officeDetailsXj.getRoomId(),officeDetailsXj.getDeptId());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//说明已经被占用,那么要重新刷新再申请
				if(flag){
					return flag;
				}
			}
			
			OfficeApplyExXj officeApplyExXj = new OfficeApplyExXj();
			OfficeApplyDetailsXj officeApplyDetailsXj=new OfficeApplyDetailsXj();
			
			//设置申请信息
			for(int i = 0;i < datePeriod.length;i++){
				officeApplyExXj = new OfficeApplyExXj();
				officeApplyDetailsXj = new OfficeApplyDetailsXj();
				officeApplyDetailsXj.setId(UUIDGenerator.getUUID());
				officeApplyDetailsXj.setState(officeBoardroomApplyXj.getState());
				try {
					officeApplyDetailsXj.setApplyDate(sdf2.parse(key));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				officeApplyDetailsXj.setApplyPeriod(datePeriod[i]);
				officeApplyDetailsXj.setUnitId(officeDetailsXj.getUnitId());
				officeApplyDetailsXj.setRoomId(officeDetailsXj.getRoomId());
				officeApplyDetailsXj.setDeptId(officeDetailsXj.getDeptId());
				officeApplyDetailsXjs.add(officeApplyDetailsXj);
				//设置关联信息
				officeApplyExXj.setId(UUIDGenerator.getUUID());
				officeApplyExXj.setApplyId(officeBoardroomApplyXj.getId());
				officeApplyExXj.setDetailsId(officeApplyDetailsXj.getId());
				officeApplyExXj.setUnitId(officeDetailsXj.getUnitId());
				officeApplyExXjs.add(officeApplyExXj);
			}
		}
		
		if(flag){
			return flag;
		}
		
		Map<String, String> timeMap = new HashMap<String, String>();
		String dateStr = "";
		for(String key : applyDateMap.keySet()){
			String[] datePeriod = applyDateMap.get(key).split(",");
			try {
				dateStr = sdf1.format(sdf2.parse(key));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			List<String> timePlist = new ArrayList<String>();
			for(int i = 0;i < datePeriod.length;i++){
				String startP = datePeriod[i].split("-")[0];
				String endP = datePeriod[i].split("-")[1];
				timeMap.put(startP, endP);
				timePlist.add(startP);
			}
			String[] timePs = timePlist.toArray(new String[0]);
			Arrays.sort(timePs);
			String sP = "";
			String eP = "";
			String PStr = "";
			for(int i = 0;i < timePs.length;i++){
				String startP = timePs[i];
				String endP = sortTimeP(timeMap, startP);
				if(StringUtils.isBlank(sP)){
					sP = startP;
					eP = endP;
					if(StringUtils.isBlank(PStr)){
						PStr = sP + "-" + eP;
					}else{
						PStr += "," + sP + "-" + eP;
					}
				}else{
					try {
						Date eD = sdf3.parse(eP);
						Date startD = sdf3.parse(startP);
						
						if(startD.after(eD)){
							sP = startP;
							eP = sortTimeP(timeMap, startP);
							if(StringUtils.isBlank(PStr)){
								PStr = sP + "-" + eP;
							}else{
								PStr += "," + sP + "-" + eP;
							}
						}else{
							continue;
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			content = "申请时段：" + dateStr + " " + PStr;
			OfficeBoardroomApplyXj officeBoardroomApplyXj = officeBoardroomApplyXjMap.get(key);
			officeBoardroomApplyXj.setContent(content);
			officeBoardroomApplyXjMap.put(key, officeBoardroomApplyXj);
		}
		
		officeBoardroomApplyXjService.batchSave(officeBoardroomApplyXjMap);
		
		officeApplyDetailsXjDao.batchDelete(officeApplyDetailsXjs);
		officeApplyDetailsXjDao.addofficeApplyDetailsXjs(officeApplyDetailsXjs);
		officeApplyExXjService.addOfficeApplyExXjs(officeApplyExXjs);
		
		//1,保存到详细表s
		
		
		//2,数据处理为了保存到申请表中  TODO
		
		
		//3，保存一条数据（详细数据）到申请表，级联保存管理表数据
		
		return flag;
	}
	
	private String sortTimeP(Map<String, String> timeMap, String startP){
		String endP = timeMap.get(startP);
		if(StringUtils.isBlank(endP)){
			return startP;
		}else{
			return sortTimeP(timeMap, endP);
		}
	}
	
	@Override
	public void cancel(OfficeApplyDetailsXj officeApplyDetailsXj) {
		String[] applyRooms = officeApplyDetailsXj.getApplyTimes();
		Arrays.sort(applyRooms);  //进行排序
		String id = "";//申请日期id
		String periods = "";//同一个会议室的节次拼接
		String content = "";//使用时间说明
		List<OfficeApplyDetailsXj> officeApplyDetailsXjs = new ArrayList<OfficeApplyDetailsXj>();
		OfficeBoardroomApplyXj officeBoardroomApplyXj=new OfficeBoardroomApplyXj();
		//OfficeBoardroomXj officeBoardroomXj = officeBoardroomXjService.getOfficeBoardroomXjById(officeApplyDetailsXj.getRoomId());
		
		officeBoardroomApplyXj.setId(UUIDGenerator.getUUID());//设置申请记录
		officeBoardroomApplyXj.setCreateTime(new Date());
		officeBoardroomApplyXj.setState(String.valueOf(Constants.APPLY_STATE_PASS));
		officeBoardroomApplyXj.setAuditUserId(officeApplyDetailsXj.getUserId());
		officeBoardroomApplyXj.setApplyUserId(officeApplyDetailsXj.getUserId());
		officeBoardroomApplyXj.setAuditDate(new Date());
		officeBoardroomApplyXj.setApplyDate(new Date());
		officeBoardroomApplyXj.setApplyDeptId(officeApplyDetailsXj.getDeptId());
		officeBoardroomApplyXj.setUnitId(officeApplyDetailsXj.getUnitId());
		officeBoardroomApplyXj.setRoomId(officeApplyDetailsXj.getRoomId());
		
		//撤销时段
		Map<String,String> timeMap=new TreeMap<String, String>();
		Map<String,String> timeMap2=new TreeMap<String, String>();
		for(String applyRoom:applyRooms){
			String[] idPeriod = applyRoom.split("_");
			String[] timePeriod=idPeriod[1].split("-");
			
			timeMap.put(idPeriod[0]+"_"+timePeriod[0], idPeriod[0]+"_"+timePeriod[1]);
			timeMap2.put(idPeriod[0]+"_"+timePeriod[0], idPeriod[0]+"_"+timePeriod[1]);
			
			OfficeApplyDetailsXj officeDetailsXj = new OfficeApplyDetailsXj();
			
			//设置申请信息
			officeDetailsXj.setRoomId(officeApplyDetailsXj.getRoomId());
			officeDetailsXj.setApplyPeriod(idPeriod[1]);
			SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd");
			try {
				officeDetailsXj.setApplyDate(sdf4.parse(idPeriod[0]));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			officeDetailsXj.setUserId(officeApplyDetailsXj.getUserId());
			officeDetailsXj.setUnitId(officeApplyDetailsXj.getUnitId());
			officeApplyDetailsXjs.add(officeDetailsXj);
		}
		
		//组织时间20160913_13:30 20160913_14:00;20160913_14:00 20160913_14:30
				//20160913_13:30-14:00,
				Map<String,String> desMap=new TreeMap<String, String>();
				Set<String> set = new HashSet<String>();
				for (Map.Entry<String, String> entry : timeMap.entrySet()) {
					if(set.contains(entry.getKey())){
					 continue;
					}
					set.add(entry.getKey());
					timeMap2.remove(entry.getKey());
					
					//组织时间20160913_13:30 20160913_14:00;20160913_14:00 20160913_14:30(val)
					//20160913_13:30-14:00,
					String linshiVal=entry.getValue();
					
					String val=timeMap2.get(linshiVal);
					if(val==null){
					 desMap.put(entry.getKey()+"-"+linshiVal.split("_")[1], entry.getKey()+"-"+linshiVal.split("_")[1]);
					}
					while(val!=null){
					 set.add(linshiVal);
					 String string = timeMap2.get(val);
					 if(string!=null){
						 set.add(val);
						 timeMap2.remove(val);
						 linshiVal = string;
						 val = linshiVal;
					 }else{
						 desMap.put(entry.getKey()+"-"+val.split("_")[1], entry.getKey()+"-"+val.split("_")[1]);
						 break;
					 }
					}
						
				}
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
				for(Map.Entry<String, String> entry : desMap.entrySet()){
					if(StringUtils.isBlank(id)){
						try {
							content += "撤销时段为"+sdf1.format(sdf2.parse((entry.getKey().split("_")[0])))+" ";
						} catch (ParseException e) {
							e.printStackTrace();
						}
						id = entry.getKey().split("_")[0];
					}
					if(id.equals(entry.getKey().split("_")[0])){
						if(StringUtils.isBlank(periods)){
							id=entry.getKey().split("_")[0];
							periods += entry.getKey().split("_")[1];
						}else{
							id=entry.getKey().split("_")[0];
							periods += ","+entry.getKey().split("_")[1];
						}
					}else{
						try {
							content +=periods+","+sdf1.format(sdf2.parse((entry.getKey().split("_")[0])))+" ";
						} catch (ParseException e) {
							e.printStackTrace();
						}
						id = entry.getKey().split("_")[0];
						periods =entry.getKey().split("_")[1];
					}
				}
		
		officeBoardroomApplyXj.setContent(content+" "+periods);
		officeBoardroomApplyXjService.save(officeBoardroomApplyXj);
		officeApplyDetailsXjDao.batchDelete(officeApplyDetailsXjs);
		
	}

	@Override
	public void updateStateByIds(String[] ids, String state) {
		officeApplyDetailsXjDao.updateStateByIds(ids, state);
	}

	@Override
	public boolean getApplyByApplyStartDate(String unitId, String roomId,
			Date applyEndDate) {
		return officeApplyDetailsXjDao.getApplyByApplyStartDate(unitId, roomId, applyEndDate);
	}

	public void setOfficeApplyDetailsXjDao(OfficeApplyDetailsXjDao officeApplyDetailsXjDao){
		this.officeApplyDetailsXjDao = officeApplyDetailsXjDao;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOfficeBoardroomXjService(
			OfficeBoardroomXjService officeBoardroomXjService) {
		this.officeBoardroomXjService = officeBoardroomXjService;
	}

	public void setOfficeBoardroomApplyXjService(
			OfficeBoardroomApplyXjService officeBoardroomApplyXjService) {
		this.officeBoardroomApplyXjService = officeBoardroomApplyXjService;
	}

	public void setOfficeApplyExXjService(
			OfficeApplyExXjService officeApplyExXjService) {
		this.officeApplyExXjService = officeApplyExXjService;
	}
	
}
