package net.zdsoft.office.bulletin.action;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.entity.CustomRole;
import net.zdsoft.eis.base.common.entity.CustomRoleUser;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.CustomRoleService;
import net.zdsoft.eis.base.common.service.CustomRoleUserService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.sms.dto.MsgDto;
import net.zdsoft.office.bulletin.entity.OfficeBulletin;
import net.zdsoft.office.bulletin.entity.OfficeBulletinRead;
import net.zdsoft.office.bulletin.entity.OfficeBulletinTl;
import net.zdsoft.office.bulletin.service.OfficeBulletinReadService;
import net.zdsoft.office.bulletin.service.OfficeBulletinTlService;
import net.zdsoft.office.util.Constants;
import net.zdsoft.office.util.OfficeUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 桐庐教育局定制通知ACTION
 */
@SuppressWarnings("serial")
public class BulletinTlAction extends PageAction {

	private OfficeBulletinTlService officeBulletinTlService;
	private OfficeBulletinReadService officeBulletinReadService;
	private CustomRoleService customRoleService;
	private CustomRoleUserService customRoleUserService;

	private List<OfficeBulletinTl> officeBulletinTlList;

	private OfficeBulletinTl bulletinTl;

	private String show;
	private String searchName;
	private String bulletinId;
	private String[] bulletinIds;
	private String startTime;
	private String endTime;
	private String publishName;
	private String[] orderIds;// 排序号

	private Integer topState;// 置顶状态
	private boolean manageAdmin;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String viewList() {
		manageAdmin = isPracticeAdmin(Constants.BULLETIN_MANAGE_TL);
		officeBulletinTlList = officeBulletinTlService
				.getOfficeBulletinTlListPage(getUnitId(), getLoginUser()
						.getUserId(), startTime, endTime, searchName,
						publishName, getPage());
		markWeek(officeBulletinTlList);
		return SUCCESS;
	}

	public String manageList() {
		if (StringUtils.isBlank(show)) {
			show = OfficeBulletin.STATE_ALL;
		}
		officeBulletinTlList = officeBulletinTlService
				.getOfficeBulletinManageListPage(getUnitId(), show, startTime,
						endTime, searchName, publishName, getPage());
		markWeek(officeBulletinTlList);
		return SUCCESS;
	}

	public void markWeek(List<OfficeBulletinTl> officeBulletinTlList) {
		if (CollectionUtils.isNotEmpty(officeBulletinTlList)) {
			for (OfficeBulletinTl bulletinTl : officeBulletinTlList) {
				try {
					bulletinTl.setWeekDay(OfficeUtils.dayForWeek(bulletinTl
							.getCreateTime()));
				} catch (ParseException e) {
					e.printStackTrace();
					bulletinTl.setWeekDay("");
				}
			}
		}

	}

	public String bulletinEdit() {
		if (StringUtils.isNotBlank(bulletinId)) {
			bulletinTl = officeBulletinTlService
					.getOfficeBulletinTlById(bulletinId);
		} else {
			bulletinTl = new OfficeBulletinTl();
			bulletinTl.setOrderId(0);// 默认为0
			bulletinTl.setCreateTime(new Date());
			bulletinTl.setEndType(OfficeBulletin.END_TYPE_ONEYEAR);// 默认截止时间一年
		}
		if(getLoginInfo().getUnitClass().equals(Unit.UNIT_CLASS_EDU)){
			return "edu";
		}else{
			return "school";
		}
	}

	public String saveOrUpdate() {
		try {
			if (bulletinTl.getCreateTime().after(new Date())) {
				jsonError = "创建时间不能超过当前时间！";
				return SUCCESS;
			}

			if (bulletinTl.getCreateTime() == null) {
				bulletinTl.setCreateTime(new Date());
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(bulletinTl.getCreateTime());
			// 设置截止时间
			if (OfficeBulletin.END_TYPE_PERMANENT.equals(bulletinTl
					.getEndType())) {
				bulletinTl.setEndTime(null);
			} else if (OfficeBulletin.END_TYPE_ONEYEAR.equals(bulletinTl
					.getEndType())) {
				calendar.add(Calendar.YEAR, 1);
				bulletinTl.setEndTime(calendar.getTime());
			} else if (OfficeBulletin.END_TYPE_HALFYEAR.equals(bulletinTl
					.getEndType())) {
				calendar.add(Calendar.MONTH, 6);
				bulletinTl.setEndTime(calendar.getTime());
			} else if (OfficeBulletin.END_TYPE_ONEMONTH.equals(bulletinTl
					.getEndType())) {
				calendar.add(Calendar.MONTH, 1);
				bulletinTl.setEndTime(calendar.getTime());
			}
			if (OfficeBulletin.STATE_PASS.equals(bulletinTl.getState())) {
				bulletinTl.setPublishTime(new Date());
			}
			if (StringUtils.isBlank(bulletinTl.getCreateUserId())) {
				bulletinTl.setCreateUserId(getLoginUser().getUserId());
			}
			bulletinTl.setIsTop(false);
			bulletinTl.setUnitId(getUnitId());
			bulletinTl.setIsDeleted(0);
			bulletinTl.setModifyUserId(getLoginUser().getUserId());
			
			//发短信	TODO
			MsgDto msgDto = null;
			if(OfficeBulletin.STATE_PASS.equals(bulletinTl.getState()) && bulletinTl.getIsNeedsms() != null && bulletinTl.getIsNeedsms()){
				msgDto = new MsgDto();
				msgDto.setUserId(getLoginUser().getUserId());
				msgDto.setUnitName(getLoginInfo().getUnitName());
				msgDto.setUserName(getLoginInfo().getUser().getName());
				msgDto.setContent("通知：" + bulletinTl.getSmsContent() + "【" + getLoginInfo().getUser().getRealname() + "-" + getLoginInfo().getUnitName() + "】【 OA短信提醒】");
				msgDto.setTiming(false);
			}
			
			if (StringUtils.isBlank(bulletinTl.getId())) {
				officeBulletinTlService.save(bulletinTl);
			} else {
				officeBulletinTlService.update(bulletinTl);
			}
			if(msgDto != null){
				officeBulletinTlService.sendMsg(bulletinTl, msgDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "操作失败";
		}
		return SUCCESS;
	}

	public String bulletinDelete() {
		if (StringUtils.isBlank(bulletinId)) {
			jsonError = "传入的值为空";
		} else {
			try {
				officeBulletinTlService.delete(new String[] { bulletinId },
						getLoginUser().getUserId());
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "删除失败";
			}
		}
		return SUCCESS;
	}

	public String bulletinDeleteIds() {
		if (ArrayUtils.isEmpty(bulletinIds)) {
			jsonError = "传入的值为空";
		} else {
			try {
				officeBulletinTlService.delete(bulletinIds, getLoginUser()
						.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "删除失败";
			}
		}
		return SUCCESS;
	}

	public String bulletinPublishIds() {
		if (ArrayUtils.isEmpty(bulletinIds)) {
			jsonError = "传入的值为空";
		} else {
			try {
				officeBulletinTlService.publish(bulletinIds, getLoginUser()
						.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "发布失败";
			}
		}
		return SUCCESS;
	}

	public String bulletinPublish() {
		if (StringUtils.isBlank(bulletinId)) {
			jsonError = "传入的值为空";
		} else {
			try {
				officeBulletinTlService.publish(new String[] { bulletinId },
						getLoginUser().getUserId());
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "发布失败";
			}
		}
		return SUCCESS;
	}

	public String bulletinQxPublish() {
		if (StringUtils.isBlank(bulletinId)) {
			jsonError = "传入的值为空";
		} else {
			try {
				officeBulletinTlService.qxPublish(bulletinId, getLoginUser()
						.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "取消失败";
			}
		}
		return SUCCESS;
	}

	/**
	 * 置顶及取消，topstate 0：取消，1：置顶
	 * 
	 * @return
	 */
	public String bulletinTop() {
		if (StringUtils.isBlank(bulletinId)) {
			jsonError = "传入的值为空";
		} else {
			try {
				officeBulletinTlService.top(bulletinId, topState);
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "审核失败";
			}
		}
		return SUCCESS;
	}

	public String saveOrder() {
		if (ArrayUtils.isEmpty(bulletinIds)) {
			jsonError = "传入的值为空";
		} else {
			try {
				officeBulletinTlService.saveOrder(bulletinIds, orderIds,
						getLoginUser().getUserId());
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "保存排序失败";
			}
		}
		return SUCCESS;
	}

	public String viewDetail() {
		OfficeBulletinRead obr = new OfficeBulletinRead();
		obr.setUnitId(getLoginUser().getUnitId());
		obr.setUserId(getLoginUser().getUserId());
		obr.setBulletinId(bulletinId);
		obr.setBulletinType(Constants.BULLETIN_TYPE_TL);
		officeBulletinReadService.save(obr);
		bulletinTl = officeBulletinTlService
				.getOfficeBulletinTlById(bulletinId);
		try {
			bulletinTl.setWeekDay(OfficeUtils.dayForWeek(bulletinTl
					.getCreateTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String onlyViewDetail() {
		bulletinTl = officeBulletinTlService
				.getOfficeBulletinTlById(bulletinId);
		try {
			bulletinTl.setWeekDay(OfficeUtils.dayForWeek(bulletinTl
					.getCreateTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String bulletinRemind(){//TODO
		if (StringUtils.isBlank(bulletinId)) {
			jsonError = "传入的值为空";
		} else {
			try {
				officeBulletinTlService.remind(new String[] { bulletinId },
						getLoginUser().getUserId());
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "短信发送失败";
			}
		}
		return SUCCESS;
	}
	
	public String bulletinRemindIds(){
		if (ArrayUtils.isEmpty(bulletinIds)) {
			jsonError = "传入的值为空";
		} else {
			try {
				officeBulletinTlService.remind(bulletinIds, getLoginUser()
						.getUserId());
			} catch (Exception e) {
				e.printStackTrace();
				jsonError = "短信发送失败";
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 判断其是否为各种管理员
	 */
	private boolean isPracticeAdmin(String str) {
		CustomRole role = customRoleService.getCustomRoleByRoleCode(
				getUnitId(), str);
		boolean flag;
		if (role == null) {
			flag = false;
			return flag;
		}
		List<CustomRoleUser> roleUs = customRoleUserService
				.getCustomRoleUserListByUserId(getLoginUser().getUserId());
		if (CollectionUtils.isNotEmpty(roleUs)) {
			for (CustomRoleUser ru : roleUs) {
				if (StringUtils.equals(ru.getRoleId(), role.getId())) {
					flag = true;
					return flag;
				}
			}
		}
		flag = false;
		return flag;
	}

	public List<OfficeBulletinTl> getOfficeBulletinTlList() {
		return officeBulletinTlList;
	}

	public void setOfficeBulletinTlList(
			List<OfficeBulletinTl> officeBulletinTlList) {
		this.officeBulletinTlList = officeBulletinTlList;
	}

	public OfficeBulletinTl getBulletinTl() {
		return bulletinTl;
	}

	public void setBulletinTl(OfficeBulletinTl bulletinTl) {
		this.bulletinTl = bulletinTl;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(String bulletinId) {
		this.bulletinId = bulletinId;
	}

	public String[] getBulletinIds() {
		return bulletinIds;
	}

	public void setBulletinIds(String[] bulletinIds) {
		this.bulletinIds = bulletinIds;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPublishName() {
		return publishName;
	}

	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}

	public String[] getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String[] orderIds) {
		this.orderIds = orderIds;
	}

	public Integer getTopState() {
		return topState;
	}

	public void setTopState(Integer topState) {
		this.topState = topState;
	}

	public boolean isManageAdmin() {
		return manageAdmin;
	}

	public void setManageAdmin(boolean manageAdmin) {
		this.manageAdmin = manageAdmin;
	}

	public void setOfficeBulletinTlService(
			OfficeBulletinTlService officeBulletinTlService) {
		this.officeBulletinTlService = officeBulletinTlService;
	}

	public void setOfficeBulletinReadService(
			OfficeBulletinReadService officeBulletinReadService) {
		this.officeBulletinReadService = officeBulletinReadService;
	}

	public void setCustomRoleService(CustomRoleService customRoleService) {
		this.customRoleService = customRoleService;
	}

	public void setCustomRoleUserService(
			CustomRoleUserService customRoleUserService) {
		this.customRoleUserService = customRoleUserService;
	}

}