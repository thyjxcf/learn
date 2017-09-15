package net.zdsoft.office.dailyoffice.action;

import java.util.List;

import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.office.dailyoffice.entity.OfficeTempComment;
import net.zdsoft.office.dailyoffice.service.OfficeTempCommentService;
import net.zdsoft.office.util.Constants;

import org.apache.commons.lang.StringUtils;

/**
 * @author chens 2016-5-16下午3:53:07
 */
public class TempCommentAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private String type;
	private String id;
	private String tempObjectId;
	private String tempObjectName;
	private OfficeTempComment officeTempComment;

	private List<OfficeTempComment> templateList;
	private OfficeTempCommentService officeTempCommentService;
	private UserService userService;

	@Override
	public String execute() throws Exception {
		if (StringUtils.isBlank(type)
				|| type.equals(Constants.TEMP_COMMENT_SYSTEM)) {
			type = Constants.TEMP_COMMENT_SYSTEM;
			templateList = officeTempCommentService
					.getOfficeTempCommentListByObjectId(BaseConstant.ZERO_GUID);
		} else if (type.equals(Constants.TEMP_COMMENT_UNIT)) {
			templateList = officeTempCommentService
					.getOfficeTempCommentListByObjectId(getLoginInfo()
							.getUnitID());
		} else if (type.equals(Constants.TEMP_COMMENT_DEPT)) {
			templateList = officeTempCommentService
					.getOfficeTempCommentListByObjectId(userService
							.getDeptIdByUserId(getLoginUser().getUserId()));
		} else if (type.equals(Constants.TEMP_COMMENT_USER)) {
			templateList = officeTempCommentService
					.getOfficeTempCommentListByObjectId(getLoginUser()
							.getUserId());
		}
		return SUCCESS;
	}

	public String tempCommentEdit(){
		if (StringUtils.isNotBlank(id)){
			officeTempComment = officeTempCommentService.getOfficeTempCommentById(id);
		} 
		return SUCCESS;
	}
	public String tempCommentRemove(){
		try{
			if (StringUtils.isNotBlank(id)){
				officeTempCommentService.delete(new String[]{id});
				promptMessageDto.setOperateSuccess(true);
				promptMessageDto.setPromptMessage("删除成功！");
			}else{
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("您删除的模板不存在！");
			}
		}catch(Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("删除失败！"+e.getMessage());
		}
		return SUCCESS;
	}

	public String tempCommentSave() {
		if (type.equals(Constants.TEMP_COMMENT_SYSTEM)) {
			officeTempComment.setObjectId(BaseConstant.ZERO_GUID);
		}else if (type.equals(Constants.TEMP_COMMENT_UNIT)) {
			officeTempComment.setObjectId(getLoginInfo().getUnitID());
		} else if (type.equals(Constants.TEMP_COMMENT_DEPT)) {
			officeTempComment.setObjectId(userService.getDeptIdByUserId(getLoginUser().getUserId()));
		} else if (type.equals(Constants.TEMP_COMMENT_USER)) {
			officeTempComment.setObjectId(getLoginUser().getUserId());
		}
		try{
			if(StringUtils.isNotBlank(officeTempComment.getId())){
				officeTempCommentService.update(officeTempComment);
			}else{
				officeTempCommentService.save(officeTempComment);
			}
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("保存成功！");
		}catch(Exception e){
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("保存失败！"+e.getMessage());
		}
		return SUCCESS;
	}
	
	public String tempSelectDiv(){
		return SUCCESS;
	}
	
	public String tempSelectList(){
		if (StringUtils.isBlank(type)
				|| type.equals(Constants.TEMP_COMMENT_SYSTEM)) {
			type = Constants.TEMP_COMMENT_SYSTEM;
			templateList = officeTempCommentService
					.getOfficeTempCommentListByObjectId(BaseConstant.ZERO_GUID);
		} else if (type.equals(Constants.TEMP_COMMENT_UNIT)) {
			templateList = officeTempCommentService
					.getOfficeTempCommentListByObjectId(getLoginInfo()
							.getUnitID());
		} else if (type.equals(Constants.TEMP_COMMENT_DEPT)) {
			templateList = officeTempCommentService
					.getOfficeTempCommentListByObjectId(userService
							.getDeptIdByUserId(getLoginUser().getUserId()));
		} else if (type.equals(Constants.TEMP_COMMENT_USER)) {
			templateList = officeTempCommentService
					.getOfficeTempCommentListByObjectId(getLoginUser()
							.getUserId());
		}
		return SUCCESS;
	}
	
	public String tempCommentView(){
		officeTempComment = officeTempCommentService.getOfficeTempCommentById(id);
		return SUCCESS;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<OfficeTempComment> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<OfficeTempComment> templateList) {
		this.templateList = templateList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTempObjectId() {
		return tempObjectId;
	}

	public void setTempObjectId(String tempObjectId) {
		this.tempObjectId = tempObjectId;
	}

	public String getTempObjectName() {
		return tempObjectName;
	}

	public void setTempObjectName(String tempObjectName) {
		this.tempObjectName = tempObjectName;
	}
	
	public OfficeTempComment getOfficeTempComment() {
		return officeTempComment;
	}

	public void setOfficeTempComment(OfficeTempComment officeTempComment) {
		this.officeTempComment = officeTempComment;
	}

	public void setOfficeTempCommentService(
			OfficeTempCommentService officeTempCommentService) {
		this.officeTempCommentService = officeTempCommentService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
