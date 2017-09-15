package net.zdsoft.office.customer.action;

import java.util.List;

import net.zdsoft.eis.base.common.action.ObjectDivAction;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.office.customer.service.OfficeCustomerApplyService;

public class CopyUserDivAction extends ObjectDivAction<User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1256754851851305562L;

	private OfficeCustomerApplyService officeCustomerApplyService;
	
	public CopyUserDivAction() {
		setShowLetterIndex(true);
	}
	
	@Override
	protected List<User> getDatasByUnitId() {
		return officeCustomerApplyService.getCpopyUserList(getLoginUser().getUnitId(), getLoginUser().getUserId());
	}

	@Override
	protected void toObject(User user, SimpleObject object) {
		if (user == null) {
			return;
		}
		if (object == null) {
			return;
		}
		object.setId(user.getId());
		object.setObjectName(user.getRealname());
		object.setUnitId(user.getUnitid());
	}

	public void setOfficeCustomerApplyService(
			OfficeCustomerApplyService officeCustomerApplyService) {
		this.officeCustomerApplyService = officeCustomerApplyService;
	}
}
