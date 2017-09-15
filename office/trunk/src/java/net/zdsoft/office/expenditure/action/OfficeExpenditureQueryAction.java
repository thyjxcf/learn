package net.zdsoft.office.expenditure.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.filters.StringInputStream;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.DeptService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.frame.action.PageSemesterAction;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.leadin.doc.DocumentHandler;
import net.zdsoft.office.expenditure.constant.ExpenConstants;
import net.zdsoft.office.expenditure.entity.OfficeExpenditure;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureBusTrip;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureMetting;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureOutlay;
import net.zdsoft.office.expenditure.entity.OfficeExpenditureReception;
import net.zdsoft.office.expenditure.service.OfficeExpenditureBusTripService;
import net.zdsoft.office.expenditure.service.OfficeExpenditureChgOpinionService;
import net.zdsoft.office.expenditure.service.OfficeExpenditureKjOpinionService;
import net.zdsoft.office.expenditure.service.OfficeExpenditureMettingService;
import net.zdsoft.office.expenditure.service.OfficeExpenditureOutlayService;
import net.zdsoft.office.expenditure.service.OfficeExpenditureReceptionService;
import net.zdsoft.office.expenditure.service.OfficeExpenditureService;
import net.zdsoft.office.util.Constants;

public class OfficeExpenditureQueryAction extends OfficeExpenditureCommonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private OfficeExpenditureService officeExpenditureService;
	private OfficeExpenditureMettingService officeExpenditureMettingService;
	private OfficeExpenditureReceptionService officeExpenditureReceptionService;
	private OfficeExpenditureOutlayService officeExpenditureOutlayService;
	private OfficeExpenditureBusTripService officeExpenditureBusTripService;
	private UserService userService;
	private DeptService deptService;
	private UnitService unitService;
	
	private OfficeExpenditure item = new OfficeExpenditure();
	private List<OfficeExpenditure> officeExpenditures=new ArrayList<OfficeExpenditure>();
	private OfficeExpenditureMetting metting = new OfficeExpenditureMetting();
	private OfficeExpenditureReception reception = new OfficeExpenditureReception();
	private OfficeExpenditureOutlay outlay = new OfficeExpenditureOutlay();
	private OfficeExpenditureBusTrip busTrip = new OfficeExpenditureBusTrip();
	
	
	private String type;
	
	private String id;
	private String userName;
	
	private String fromTab;
	private boolean print;
	
	private String unitName;
	
	public String execute(){
		Set<String> queryUserIds = new HashSet<String>();
		if(isAdminAuth()){
			if(StringUtils.isNotBlank(userName)){
				List<User> ulist = userService.getUsersByFaintness(getUnitId(), userName);
				Iterator<User> iterator = ulist.iterator();
				while(iterator.hasNext()){
					queryUserIds.add(iterator.next().getId());
				}
				officeExpenditures = officeExpenditureService.getOfficeExpendituresByUserIds(getUnitId(), queryUserIds.toArray(new String[0]), type, Constants.LEAVE_APPLY_FLOW_FINSH_PASS+"", getPage());
			}else{
				//搜所有
				officeExpenditures = officeExpenditureService.getOfficeExpendituresByUserIds(getUnitId(), null, type, Constants.LEAVE_APPLY_FLOW_FINSH_PASS+"", getPage());
			}
		}else{
			queryUserIds.add(getLoginUser().getUserId());
			officeExpenditures = officeExpenditureService.getOfficeExpendituresByUserIds(getUnitId(), queryUserIds.toArray(new String[0]), type, Constants.LEAVE_APPLY_FLOW_FINSH_PASS+"", getPage());
		}
		
		Iterator<OfficeExpenditure> iterator =  officeExpenditures.iterator();
		Set<String> userIds = new HashSet<String>();
		Set<String> ids1 = new HashSet<String>();
		Set<String> ids2 = new HashSet<String>();
		Set<String> ids3 = new HashSet<String>();
		Set<String> ids4 = new HashSet<String>();
		while(iterator.hasNext()){
			OfficeExpenditure ent = iterator.next();
			userIds.add(ent.getApplyUserId());
			
			if(ExpenConstants.TYPE_1.equals(ent.getType())){
				ids1.add(ent.getId());
			}else if(ExpenConstants.TYPE_2.equals(ent.getType())){
				ids2.add(ent.getId());
			}else if(ExpenConstants.TYPE_3.equals(ent.getType())){
				ids3.add(ent.getId());
			}else if(ExpenConstants.TYPE_4.equals(ent.getType())){
				ids4.add(ent.getId());
			}
		}

		Map<String, OfficeExpenditureMetting> map1 = officeExpenditureMettingService.getOfficeExpenditureMettingByExIds(ids1.toArray(new String[0]));
		Map<String, OfficeExpenditureReception> map2 = officeExpenditureReceptionService.getOfficeExpenditureReceptionByExIds(ids2.toArray(new String[0]));
		Map<String, OfficeExpenditureOutlay> map3 = officeExpenditureOutlayService.getOfficeExpenditureOutlayByExIds(ids3.toArray(new String[0]));
		Map<String, OfficeExpenditureBusTrip> map4 = officeExpenditureBusTripService.getOfficeExpenditureBusTripByExIds(ids4.toArray(new String[0]));
		
		
		Map<String, User> map = userService.getUsersMap(userIds.toArray(new String[0]));
		Map<String, Dept> deptMap = deptService.getDeptMap(getUnitId());
		for(OfficeExpenditure ent : officeExpenditures){
			if(map.containsKey(ent.getApplyUserId())){
				User user = map.get(ent.getApplyUserId());
				if(user!=null){
					ent.setApplyUserName(user.getRealname());
					if(deptMap.containsKey(user.getDeptid())){
						Dept dept = deptMap.get(user.getDeptid());
						if(dept!=null)
							ent.setApplyUserDeptName(dept.getDeptname());
					}
				}
			}
			//费用合计
			if(ExpenConstants.TYPE_1.equals(ent.getType())){
				if(map1.containsKey(ent.getId())){
					ent.setFee(map1.get(ent.getId()).getSum());
				}
			}else if(ExpenConstants.TYPE_2.equals(ent.getType())){
				if(map2.containsKey(ent.getId())){
					ent.setFee(map2.get(ent.getId()).getSum());
				}
			}else if(ExpenConstants.TYPE_3.equals(ent.getType())){
				if(map3.containsKey(ent.getId())){
					ent.setFee(map3.get(ent.getId()).getSum());
				}
			}else if(ExpenConstants.TYPE_4.equals(ent.getType())){
				if(map4.containsKey(ent.getId())){
					ent.setFee(map4.get(ent.getId()).getSum());
				}
			}
		}
		
		return SUCCESS;
	}
	
	public String detail(){
		item = officeExpenditureService.getOfficeExpenditureById(id);
		Unit unit=unitService.getUnit(item.getUnitId());
		if(unit!=null){
			unitName=unit.getName();
		}
		if(item!=null){
			if(ExpenConstants.TYPE_1.equals(item.getType())){
				metting = officeExpenditureMettingService.getOfficeExpenditureMettingByPrimarId(item.getId());
				return "metting";
			}else if(ExpenConstants.TYPE_2.equals(item.getType())){
				reception = officeExpenditureReceptionService.getOfficeExpenditureReceptionByExId(item.getId());
				return "reception";
			}else if(ExpenConstants.TYPE_3.equals(item.getType())){
				outlay = officeExpenditureOutlayService.getOfficeExpenditureOutlayByExId(item.getId());
				return "outlay";
			}else if(ExpenConstants.TYPE_4.equals(item.getType())){
				busTrip = officeExpenditureBusTripService.getOfficeExpenditureBusTripByExId(item.getId());
				return "busTrip";
			}
		}
		
		return SUCCESS;
	}
	
	public void export(){
		item = officeExpenditureService.getOfficeExpenditureById(id);
		Unit unit=unitService.getUnit(item.getUnitId());
		if(unit!=null){
			unitName=unit.getName();
		}
		Map<String, Object> dataMap=new HashMap<String, Object>();
		dataMap.put("unitName", unitName);
		dataMap.put("item", item);
		
		
		String docName = "";
		String xmlFileName = "";
		if(item!=null){
			if(ExpenConstants.TYPE_1.equals(item.getType())){
				docName = "会议费";
				xmlFileName = "expenditure_meeting.xml";
				metting = officeExpenditureMettingService.getOfficeExpenditureMettingByPrimarId(item.getId());
				dataMap.put("metting", metting);
			}else if(ExpenConstants.TYPE_2.equals(item.getType())){
				docName = "公务接待费";
				xmlFileName = "expenditure_reception.xml";
				reception = officeExpenditureReceptionService.getOfficeExpenditureReceptionByExId(item.getId());
				dataMap.put("reception", reception);
			}else if(ExpenConstants.TYPE_3.equals(item.getType())){
				docName = "经费（除会议接待出差外）";
				xmlFileName = "expenditure_outlay.xml";
				outlay = officeExpenditureOutlayService.getOfficeExpenditureOutlayByExId(item.getId());
				dataMap.put("outlay", outlay);
			}else if(ExpenConstants.TYPE_4.equals(item.getType())){
				docName = "出差和下乡";
				xmlFileName = "expenditure_bustrip.xml";
				busTrip = officeExpenditureBusTripService.getOfficeExpenditureBusTripByExId(item.getId());
				dataMap.put("busTrip", busTrip);
			}
		}
		if(StringUtils.isBlank(xmlFileName))
			return;
		try {
			String s=DocumentHandler.createDocString(dataMap, xmlFileName);
			InputStream in=new StringInputStream(s, "utf-8");
			ServletUtils.download(in, getRequest(), getResponse(), docName+"开支申请表.doc");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<OfficeExpenditure> getOfficeExpenditures() {
		return officeExpenditures;
	}
	
	public void setOfficeExpenditureService(
			OfficeExpenditureService officeExpenditureService) {
		this.officeExpenditureService = officeExpenditureService;
	}

	public void setOfficeExpenditureMettingService(
			OfficeExpenditureMettingService officeExpenditureMettingService) {
		this.officeExpenditureMettingService = officeExpenditureMettingService;
	}

	public void setOfficeExpenditureReceptionService(
			OfficeExpenditureReceptionService officeExpenditureReceptionService) {
		this.officeExpenditureReceptionService = officeExpenditureReceptionService;
	}

	public void setOfficeExpenditureOutlayService(
			OfficeExpenditureOutlayService officeExpenditureOutlayService) {
		this.officeExpenditureOutlayService = officeExpenditureOutlayService;
	}

	public void setOfficeExpenditureBusTripService(
			OfficeExpenditureBusTripService officeExpenditureBusTripService) {
		this.officeExpenditureBusTripService = officeExpenditureBusTripService;
	}

	public OfficeExpenditureMetting getMetting() {
		return metting;
	}

	public void setMetting(OfficeExpenditureMetting metting) {
		this.metting = metting;
	}

	public OfficeExpenditureReception getReception() {
		return reception;
	}

	public void setReception(OfficeExpenditureReception reception) {
		this.reception = reception;
	}

	public OfficeExpenditureOutlay getOutlay() {
		return outlay;
	}

	public void setOutlay(OfficeExpenditureOutlay outlay) {
		this.outlay = outlay;
	}

	public OfficeExpenditureBusTrip getBusTrip() {
		return busTrip;
	}

	public void setBusTrip(OfficeExpenditureBusTrip busTrip) {
		this.busTrip = busTrip;
	}

	public OfficeExpenditure getItem() {
		return item;
	}

	public void setItem(OfficeExpenditure item) {
		this.item = item;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFromTab() {
		return fromTab;
	}

	public void setFromTab(String fromTab) {
		this.fromTab = fromTab;
	}

	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	
}
