/*
* Project: v6
* Author : shenke
* @(#) TcNoticeAction.java Created on 2016-10-25
* @Copyright (c) 2016 ZDSoft Inc. All rights reserved
*/
package net.zdsoft.office.desktop.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.common.service.TeachAreaService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
import net.zdsoft.office.bulletin.service.OfficeBulletinService;
import net.zdsoft.office.msgcenter.dto.MessageSearch;
import net.zdsoft.office.msgcenter.entity.OfficeMsgReceiving;
import net.zdsoft.office.msgcenter.service.OfficeMsgReceivingService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @description: 天长定制（通知）（办公消息，通知公告 合并）
 * @author: shenke
 * @version: 1.0
 * @date: 2016-10-25下午2:59:50
 */
public class TcNoticeAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//办公公告
	private OfficeBulletinService officeBulletinService;
	//private OfficeBulletinTypeService officeBulletinTypeService;
	
	//办公消息
	private OfficeMsgReceivingService officeMsgReceivingService; 
	private ModuleService moduleService;
	private UnitService unitService;
	private TeachAreaService teachAreaService;
	
	private List<TcNotice> tcs = Lists.newArrayList();
	private Module module;
	private Integer dataSize = 0;
	
	private Map<String,Unit> unitMap ;
	private Map<String,TeachArea> areaMap ;
	
	public String execute(){
		
		String bulletinType = "3";
		Pagination page = new Pagination(1, 20, false);
		//List<OfficeBulletin> officeBulletins = officeBulletinService.getOfficeBulletinListPage(bulletinType, getLoginInfo().getUnitID(), null, OfficeBulletin.STATE_PASS, getLoginInfo().getUnitClass(), page);
		List<OfficeBulletin> officeBulletins = officeBulletinService.getOfficeBulletinListPage(new String[] {bulletinType},getLoginInfo().getUnitID(), getLoginUser().getUserId(), null, OfficeBulletin.STATE_PASS, null, null, null, null, page);
		MessageSearch messageSearch = new MessageSearch();
		messageSearch.setReceiveUserId(getLoginUser().getUserId());
		page = new Pagination(1, 20, false);
		List<OfficeMsgReceiving> msgReceivings = officeMsgReceivingService.getOfficeMsgReceivingList(messageSearch, page);
		
		//init unitMap
		Set<String> uIds = Sets.newHashSet();
		if(CollectionUtils.isNotEmpty(officeBulletins)){
			this.<OfficeBulletin>getUnitIds(officeBulletins, uIds);
			unitMap = unitService.getUnitMap(uIds.toArray(new String[0]));
		}
		//init areaMap
		areaMap = teachAreaService.getTeachAreaMap(getUnitId());
		
		
		List<TcNotice> objs = Lists.newArrayList();
		
		objs = wrapper(officeBulletins);
		List<TcNotice> newObjs = wrapper(msgReceivings);
		if(CollectionUtils.isNotEmpty(newObjs)){
			objs.addAll(newObjs);
		}
		
		//按时间降序
		Collections.sort(objs,new Comparator<TcNotice>() {
			@Override
			public int compare(TcNotice o1, TcNotice o2) {
				return o2.getNoticeDate().compareTo(o1.getNoticeDate());
			}
		});
		
		if(objs.size()>20)
			tcs = objs.subList(0, 20);
		else
			tcs = objs;
		
		int t = 0;
		for(TcNotice tc : tcs){
			if(tc.getTitleLength()>13){
				t += 2;
			}else{
				t += 1;
			}
		}
		dataSize = t;
		
		return SUCCESS;
	}

	
//	public void setOfficeBulletinTypeService(
//			OfficeBulletinTypeService officeBulletinTypeService) {
//		this.officeBulletinTypeService = officeBulletinTypeService;
//	}

	public void setOfficeBulletinService(OfficeBulletinService officeBulletinService) {
		this.officeBulletinService = officeBulletinService;
	}
	
	public void setOfficeMsgReceivingService(
			OfficeMsgReceivingService officeMsgReceivingService) {
		this.officeMsgReceivingService = officeMsgReceivingService;
	}

	public List<TcNotice> getTcs() {
		return tcs;
	}

	public Module getModule() {
		return module;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}


	private <T> List<TcNotice> wrapper(List<T> os){
		
		List<TcNotice> tcs = Lists.newArrayList();
		for(T t : os){
			TcNotice tc = new TcNotice(t);
			tcs.add(tc);
		}
		return tcs;
	}
	
	private <T> void getUnitIds(List<T> os, Set<String> sets){
		for(T t : os){
			try {
				String id = BeanUtils.getProperty(t, "unitId");
				sets.add(id);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	public Integer getDataSize() {
		return dataSize;
	}

	

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}


	public void setTeachAreaService(TeachAreaService teachAreaService) {
		this.teachAreaService = teachAreaService;
	}



	/**
	 * wrapper for OfficeMsgReceiving and OfficeBulletin
	 * @author shenke
	 *
	 */
	public class TcNotice {
		
		private OfficeMsgReceiving officeMsgReceiving;
		private OfficeBulletin officeBulletin;
		
		private Object curreObject;
		
		private Date noticeDate;
		private String javaScript;
		private String subTitle; //【中的内容】
		
		public TcNotice(Object curreObject) {
			super();
			this.curreObject = curreObject;
			
			init(); 
		}
		
		public Date getNoticeDate(){
			return this.noticeDate;
		}
		
		public String getDateStr(){
			return net.zdsoft.keel.util.DateUtils.date2String(this.noticeDate, "MM-dd");
		}
		
		public String getTitle(){
			String title = StringUtils.EMPTY;
			try {
				title = BeanUtils.getProperty(curreObject, "title");
				//title = "你好你好你好你好你好你好你好你好你好你好你好你好你好你好";
				if(getLength(title)>24){
					title = StringUtils.substring(title, 0, 23)+"...";
				}
			} catch (Exception e) {
				return title;
			}
			return title;
		}
		
		public int getTitleLength(){
			return StringUtils.length(getTitle());
		}
		
		public String getJavaScript(){
			return this.javaScript;
		}
		
		public String getSubTitle(){
			return this.subTitle;
		}
		//这里能否进行优化?
		private void init(){
			int moduleId = 69002;
			if(getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_EDU){
				moduleId = 69052;
			}
			module = moduleService.getModuleByIntId(moduleId);
			
			if(curreObject instanceof OfficeBulletin){
				this.officeBulletin = (OfficeBulletin) curreObject;
				this.noticeDate = officeBulletin.getCreateTime();
				Unit bUnit = unitMap.get(officeBulletin.getUnitId());
				
				if(bUnit == null){
					this.subTitle = "未知";
				}
				
				else if(StringUtils.equals(bUnit.getUnitclass()+"", "1")){
					this.subTitle = "教育局";
				}else{
					if(BaseConstant.ZERO_GUID.equals(officeBulletin.getAreaId())){
						this.subTitle = "全校";
					}else{
						if(areaMap.get(officeBulletin.getAreaId())!=null){
							this.subTitle = areaMap.get(officeBulletin.getAreaId()).getAreaName();
						}else{
							this.subTitle = "校区已删除";
						}
					}
				}
				
				
				this.javaScript = new StringBuilder()
				.append("window.open('")
				.append(getRequest().getSession().getServletContext().getContextPath())
				.append("/office/desktop/app/info-viewDetail.action?bulletinId=")
				.append(officeBulletin.getId())
				.append("',"+"''"+","+"'fullscreen,scrollbars,resizable=yes,toolbar=no');")
				.toString();
			} 
			if(curreObject instanceof OfficeMsgReceiving){
				this.officeMsgReceiving = (OfficeMsgReceiving) curreObject;
				this.noticeDate = officeMsgReceiving.getSendTime();
				this.subTitle = "消息";
				this.javaScript = new StringBuilder()
				.append("go2Module(")
				.append("'"+module.getUrl()+((module.getId() == 69002 || module.getId() == 69052)?"?desktopIn=1":"")+"'")
				.append(",'"+module.getSubsystem()+"','"+module.getId()+"','"+module.getParentid()+"','"+module.getName()+"',"+"'desktop'")
				.append(");return false").toString();
			}
		}
		
	}
	private int getLength(String str){
		int length = 0;
		for(char ch : str.toCharArray()){
			if(ch >= '\u4e00' && ch <='\u9fa5'){
				length +=2;
			}else{
				length +=1;
			}
		}
		return length/2;
	}
	
}
