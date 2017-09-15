package net.zdsoft.office.remote;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
import net.zdsoft.office.bulletin.entity.OfficeBulletinRead;
import net.zdsoft.office.bulletin.service.OfficeBulletinReadService;
import net.zdsoft.office.bulletin.service.OfficeBulletinService;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class RemoteBulletinAction extends OfficeJsonBaseAction {

	private String unitId;
	private String id;
	private String userId;// 登陆用户
	private String pageLastDate;//每一页的最后通知公告的日期

	private List<OfficeBulletin> officeBulletinList;

	private OfficeBulletinService officeBulletinService;
	private OfficeBulletinReadService officeBulletinReadService;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public void bulletinList() {
		Pagination page = getPage();
		JSONArray array = new JSONArray();
		//查询通知和公告的方法1,3
		officeBulletinList = officeBulletinService.getOfficeBulletinListPage(
				new String[]{"1","3"}, unitId, userId, null, OfficeBulletin.STATE_PASS, null,
				null, null, null, page);
		if(StringUtils.isBlank(pageLastDate)){
			pageLastDate="";
		}
		OfficeBulletin priorBulletin = new OfficeBulletin();
		for(OfficeBulletin bulletin:officeBulletinList){
			String createTime = DateUtils.date2String(bulletin.getCreateTime(), "yyyy-MM-dd");
			if(!pageLastDate.equals(createTime)){
				pageLastDate = createTime;
				bulletin.setFirst("1");
				priorBulletin.setLast("1");
			}else{
				bulletin.setFirst("0");
				priorBulletin.setLast("0");
			}
			priorBulletin=bulletin;
		}
		String today = DateUtils.date2String(new Date(), "yyyy-MM-dd");
		String yesterday = DateUtils.date2String(new Date(new Date().getTime()-24*60*60*1000), "yyyy-MM-dd");
		for(OfficeBulletin bulletin:officeBulletinList){
			JSONObject json = new JSONObject();
			String date = DateUtils.date2String(bulletin.getCreateTime(), "yyyy-MM-dd");
			if(today.equals(date)){
				date="今天";
			}
			if(yesterday.equals(date)){
				date="昨天";
			}
			json.put("id", bulletin.getId());
			json.put("first", bulletin.getFirst());
			json.put("last", bulletin.getLast());
			if(StringUtils.isNotBlank(bulletin.getTitle())&&bulletin.getTitle().length()>35){
				json.put("title", bulletin.getTitle().substring(0,25)+"...");
			}else{
				json.put("title", bulletin.getTitle());
			}
			json.put("type", bulletin.getType());
			json.put("date", date);
			array.add(json);
		}
		jsonMap.put(getListObjectName(), array);
		jsonMap.put("result", 1);
		jsonMap.put("page", page);
		responseJSON(jsonMap);
	}

	public void bulletinDetail() {
		JSONObject json = new JSONObject();
		OfficeBulletin bulletin = new OfficeBulletin();
		bulletin = officeBulletinService.getOfficeBulletinById(id);
		OfficeBulletinRead obr = new OfficeBulletinRead();
		obr.setUnitId(unitId);
		obr.setUserId(userId);
		obr.setBulletinType(bulletin.getType());
		obr.setBulletinId(id);
		officeBulletinReadService.save(obr);
		json.put("title", bulletin.getTitle());
		json.put("nameAndDept", bulletin.getCreateUserName()+"("+bulletin.getDeptName()+")");
		json.put("createTime", DateUtils.date2String(bulletin.getCreateTime(), "yyyy-MM-dd HH:mm"));
		json.put("content", bulletin.getContent());
		jsonMap.put(getDetailObjectName(), json);
		jsonMap.put("result", 1);
		responseJSON(jsonMap);
	}

	@Override
	protected String getListObjectName() {
		return "result_array";
	}

	@Override
	protected String getDetailObjectName() {
		return "result_object";
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<OfficeBulletin> getOfficeBulletinList() {
		return officeBulletinList;
	}

	public void setOfficeBulletinList(List<OfficeBulletin> officeBulletinList) {
		this.officeBulletinList = officeBulletinList;
	}

	public void setOfficeBulletinService(
			OfficeBulletinService officeBulletinService) {
		this.officeBulletinService = officeBulletinService;
	}
	
	public void setOfficeBulletinReadService(
			OfficeBulletinReadService officeBulletinReadService) {
		this.officeBulletinReadService = officeBulletinReadService;
	}

	public String getPageLastDate() {
		return pageLastDate;
	}

	public void setPageLastDate(String pageLastDate) {
		this.pageLastDate = pageLastDate;
	}

}
