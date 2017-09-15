package net.zdsoft.eis.base.auditflow.template.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.zdsoft.eis.base.auditflow.template.service.JwAuditFlowManageService;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.frame.action.BaseAction;

public class JwAuditFlowManageAction extends BaseAction{
	
	private UnitService unitService;
	
	private String auditType;   //用来记录选择的移动转学类型
	private String regionLevel; //记录系统的最高的系统级别（2省教育局，3市教育局，4区县教育局）
	private String refurbish;   //添加审核单位时记录状态
	private String sessionState;//记录session保存情况，no表示没有记录，yes表示有记录。
	private String businessType = "-255";//异动类型，现在都是-255，所有的类型都用同一个
	private String selectType;  //选择的异动类型，1为转学异动类型 2为其他异动类型
	private String section;     //标示学段
	//private String selectInfo;  //记录选择信息
	private List flowTypeList;
	private List auditFlowList;
	private List auditTypeList;
	private List auditFlowListNew;
	private JwAuditFlowManageService jwAuditFlowManageService;
	private String bltest;
	private String result;
	private boolean showFlowType = true;//是否显示异动类型选项
	private boolean showSections = true;//是否显示学段选项
	private boolean schConfirm = false;//是否需要学校确认这步
	private boolean showDefault = false;//是否显示已经设置好的默认流程  如果显示默认流程，需要传参businessType并且数据库里要存在该流程
	private String outerframe = "subIframe";//计算高度用 iframe的id
	private Integer nowRegionLevel;//当前登录单位
	
	/*
	 * 根据系统的最高审核单位级别，列出此异动所有可能出现的情况
	 */
	public String showAuditTypeList(){
		try {			
			if(selectType == null || selectType.length() <= 0)
				selectType = "1";
			regionLevel = jwAuditFlowManageService.getRegionLevel()+"";
			if(getLoginInfo() == null){
				nowRegionLevel = Integer.valueOf(regionLevel);
				auditTypeList = jwAuditFlowManageService.getAuditTypeList(null);
			}else{
				Unit unit = unitService.getUnit(getUnitId());
				nowRegionLevel = unit.getRegionlevel();
				auditTypeList = jwAuditFlowManageService.getAuditTypeList(nowRegionLevel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	
	/*
	 * 审核步骤初始化展示
	 */
	public String showAuditFlowList(){
		
		try {		
			if(jwAuditFlowManageService.getAuditFlowList(businessType, auditType, section))
				bltest = "";
			else
				bltest = "此审核流程未设置，显示的为默认值，请确认后点击保存";
			if(result != null)
				result = "保存成功！";
			
			auditFlowList = jwAuditFlowManageService.getNonentityNoteData(regionLevel, nowRegionLevel, auditType, schConfirm);
			auditFlowListNew = jwAuditFlowManageService.getAuditFlowList(regionLevel, nowRegionLevel, businessType, auditType, section, schConfirm);
			getRequest();
			HttpSession session = request.getSession();
			session.setAttribute("auditFlowListNew", auditFlowListNew);
			sessionState = "no";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/*
	 * 添加审核步骤和审核单位
	 */
	public String refurbishAuditFlowList(){
		try {
			getRequest();
			HttpSession session = request.getSession();
			auditFlowList = jwAuditFlowManageService.getNonentityNoteData(regionLevel, nowRegionLevel, auditType, schConfirm);
			String[] refurbishs = refurbish.split("--");//0插入位置序号,1插入的单位序号,2现存的单位序号
			String[] str = (String[])auditFlowList.get(Integer.parseInt(refurbishs[1])-1);
			String[] unitNew = {str[0],str[1],str[2],str[3],str[4],str[5]};	
			if(sessionState.equals("no")){
				auditFlowListNew = new ArrayList();
				List list = jwAuditFlowManageService.getAuditFlowList(regionLevel, nowRegionLevel, businessType, auditType, section, schConfirm);
				for (int i = 0; i < list.size(); i++) {
					String[] unit = (String[])list.get(i);
					if(unit[0].equals(refurbishs[0])){
						unitNew[0] = auditFlowListNew.size()+1+"";
						auditFlowListNew.add(unitNew);
						unit[0] = auditFlowListNew.size()+1+"";
						auditFlowListNew.add(unit);
					}else if(refurbishs[2].indexOf(unit[0])>=0){
						unit[0] = auditFlowListNew.size()+1+"";
						auditFlowListNew.add(unit);
					}
				}
				sessionState = "yes";
				session.setAttribute("auditFlowListNew", auditFlowListNew);
			}else{
				auditFlowListNew = (List)session.getAttribute("auditFlowListNew");
				List list= new ArrayList();
				for (int i = 0; i < auditFlowListNew.size(); i++) {
					String[] unit = (String[])auditFlowListNew.get(i);
					if(unit[0].equals(refurbishs[0])){
						unitNew[0] = list.size()+1+"";
						list.add(unitNew);
						unit[0] = list.size()+1+"";
						list.add(unit);
					}else if(refurbishs[2].indexOf(unit[0])>=0){
						unit[0] = list.size()+1+"";
						list.add(unit);
					}
				}
				auditFlowListNew = list;
				session.setAttribute("auditFlowListNew", auditFlowListNew);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/*
	 * 最终审核步骤保存
	 */
	public String addAuditFlowList(){
		try {
			sessionState="yes";
			String[] auditTypes = auditType.split("-");
			getRequest();
			HttpSession session = request.getSession();
			auditFlowListNew = (List)session.getAttribute("auditFlowListNew");
			auditFlowList= new ArrayList();
			for (int i = 1; i < auditFlowListNew.size(); i++) {
				String[] unit = (String[])auditFlowListNew.get(i);
				String str = "-"+unit[0]+"-";
				if(refurbish.indexOf(str)>=0){
					auditFlowList.add(unit);
				}
			}
			//进行清除以前数据
			jwAuditFlowManageService.deleteAuditFlow(auditType,section,businessType);
			jwAuditFlowManageService.addAuditFlow(auditType,section,businessType,auditFlowList);
			result = "yes";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	public String getRegionLevel() {
		return regionLevel;
	}
	public void setRegionLevel(String regionLevel) {
		this.regionLevel = regionLevel;
	}
	public JwAuditFlowManageService getJwAuditFlowManageService() {
		return jwAuditFlowManageService;
	}
	public void setJwAuditFlowManageService(
			JwAuditFlowManageService jwAuditFlowManageService) {
		this.jwAuditFlowManageService = jwAuditFlowManageService;
	}
	public List getAuditFlowList() {
		return auditFlowList;
	}
	public void setAuditFlowList(List auditFlowList) {
		this.auditFlowList = auditFlowList;
	}
	public List getAuditTypeList() {
		return auditTypeList;
	}
	public void setAuditTypeList(List auditTypeList) {
		this.auditTypeList = auditTypeList;
	}
	public String getRefurbish() {
		return refurbish;
	}
	public void setRefurbish(String refurbish) {
		this.refurbish = refurbish;
	}
	public List getAuditFlowListNew() {
		return auditFlowListNew;
	}
	public void setAuditFlowListNew(List auditFlowListNew) {
		this.auditFlowListNew = auditFlowListNew;
	}
	public String getSessionState() {
		return sessionState;
	}
	public void setSessionState(String sessionState) {
		this.sessionState = sessionState;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public List getFlowTypeList() {
		return flowTypeList;
	}
	public void setFlowTypeList(List flowTypeList) {
		this.flowTypeList = flowTypeList;
	}


	public String getSelectType() {
		return selectType;
	}


	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}


	public String getBltest() {
		return bltest;
	}


	public void setBltest(String bltest) {
		this.bltest = bltest;
	}

	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getBusinessType() {
		return businessType;
	}


	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public boolean isShowFlowType() {
		return showFlowType;
	}


	public void setShowFlowType(boolean showFlowType) {
		this.showFlowType = showFlowType;
	}


	public boolean isShowSections() {
		return showSections;
	}


	public void setShowSections(boolean showSections) {
		this.showSections = showSections;
	}


	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}


	public boolean isSchConfirm() {
		return schConfirm;
	}


	public void setSchConfirm(boolean schConfirm) {
		this.schConfirm = schConfirm;
	}


	public boolean isShowDefault() {
		return showDefault;
	}


	public void setShowDefault(boolean showDefault) {
		this.showDefault = showDefault;
	}


	public String getOuterframe() {
		return outerframe;
	}


	public void setOuterframe(String outerframe) {
		this.outerframe = outerframe;
	}


	public Integer getNowRegionLevel() {
		return nowRegionLevel;
	}


	public void setNowRegionLevel(Integer nowRegionLevel) {
		this.nowRegionLevel = nowRegionLevel;
	}

}
