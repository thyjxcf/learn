package net.zdsoft.office.dailyoffice.action;

import java.util.Date;
import java.util.List;

import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.office.dailyoffice.entity.OfficeReception;
import net.zdsoft.office.dailyoffice.service.OfficeReceptionService;

import org.apache.commons.lang.StringUtils;
/**
 * @author chens
 * @version 创建时间：2014-12-31 下午3:23:24
 * 
 */
public class ReceptionManageAction extends PageAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OfficeReception reception;
	private List<OfficeReception> officeReceptionList;
	private OfficeReceptionService officeReceptionService;
	private String id;
	// 以下两个字段用来查询
	private Date startTime;
	private Date endTime;
	// 我的接待
	private String receptionUserId;

	public String getReceptionUserId() {
		return receptionUserId;
	}

	public void setReceptionUserId(String receptionUserId) {
		this.receptionUserId = receptionUserId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOfficeReceptionService(
			OfficeReceptionService officeReceptionService) {
		this.officeReceptionService = officeReceptionService;
	}

	public OfficeReception getReception() {
		return reception;
	}

	public void setReception(OfficeReception reception) {
		this.reception = reception;
	}

	public List<OfficeReception> getOfficeReceptionList() {
		return officeReceptionList;
	}

	public void setOfficeReceptionList(List<OfficeReception> officeReceptionList) {
		this.officeReceptionList = officeReceptionList;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String receptionManageDelete() {
		try {
			officeReceptionService.delete(new String[] { id });
			promptMessageDto.setOperateSuccess(true);
		} catch (Exception e) {
			promptMessageDto.setErrorMessage("操作异常：" + e.getMessage());
		}
		return SUCCESS;

	}

	public String receptionManageList() {
		officeReceptionList = officeReceptionService
				.getOfficeReceptionByUnitIdWithPage(startTime,
						endTime, getUnitId(), getPage());
		return SUCCESS;
	}

	public String receptionManageAdd() {
		if (StringUtils.isBlank(id)) {
			reception = new OfficeReception();
		} else {
			reception = officeReceptionService.getOfficeReceptionById(id);
			//解决数据库中默认取到的数为0
			if(reception.getPersonNumber()==0){
				reception.setPersonNumber(null);
			}
		}
		return SUCCESS;
	}

	public String receptionManageSave() {
		if(!reception.getIsDining()){
			reception.setStandard("");
			reception.setPersonNumber(null);
		}
		if (StringUtils.isBlank(reception.getId())) {
			reception.setUnitId(getUnitId());
			reception.setCreateUserId(getLoginInfo().getUser().getId());
			try {
				officeReceptionService.save(reception);
				promptMessageDto.setOperateSuccess(true);
			} catch (Exception e) {
				promptMessageDto.setErrorMessage("操作异常：" + e.getMessage());
			}
		} else {
			try {
				officeReceptionService.update(reception);
				promptMessageDto.setOperateSuccess(true);
			} catch (Exception e) {
				promptMessageDto.setErrorMessage("操作异常：" + e.getMessage());
			}
		}
		return SUCCESS;
	}
}
