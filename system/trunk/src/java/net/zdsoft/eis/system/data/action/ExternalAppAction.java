package net.zdsoft.eis.system.data.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.storage.StorageFileUtils;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.system.data.entity.ExternalApp;
import net.zdsoft.eis.system.data.service.ExternalAppService;
import net.zdsoft.keelcnet.action.UploadFile;
import net.zdsoft.leadin.exception.FileUploadFailException;

import org.apache.commons.collections.CollectionUtils;

import com.opensymphony.xwork2.ModelDriven;

public class ExternalAppAction extends BaseAction implements
		ModelDriven<ExternalApp> {

	private static final long serialVersionUID = 1740771853439763912L;

	private List<ExternalApp> appList = new ArrayList<ExternalApp>();

	private ExternalApp externalApp = new ExternalApp();

	private UploadFile pictureFile;

	private String externalAppId;

	private ExternalAppService externalAppService;

	public String execute() {
		return SUCCESS;
	}

	public String saveApp() {
		int maxNum = 99999;
		if (BaseConstant.SYS_DEPLOY_SCHOOL_NBZX
				.equalsIgnoreCase(getSystemDeploySchool()))
			maxNum = 3;
		List<ExternalApp> appList = externalAppService
				.getExternalAppListByUnitId(getUnitId(),
						externalApp.getType(), 0, false);
		if (CollectionUtils.isNotEmpty(appList) && appList.size() >= maxNum) {
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("超出可添加的最大数量");
			return SUCCESS;
		}

		try {
			pictureFile = StorageFileUtils.handleFile(new String[] { "gif",
					"bmp", "jpg", "jpeg", "png" }, 0);
		} catch (FileUploadFailException e) {
			log.error(e.getMessage(), e);
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(e.getMessage());
			return SUCCESS;
		}
		//externalApp.setType(ExternalApp.EXTERNAL_APP);
		externalApp.setUnitId(getUnitId());
		try {
			externalAppService.addExternalApp(externalApp, pictureFile);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("添加应用成功!");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(e.getMessage());
		}
		return SUCCESS;
	}

	public String saveBroadcast() {
		externalApp.setType(ExternalApp.BROADCAST_CLASSROOM);
		externalApp.setUnitId(getUnitId());
		try {
			externalAppService.addExternalApp(externalApp,null);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("添加成功!");
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage(e.getMessage());
		}
		return SUCCESS;
	}

	public String deleteApp() {
		try {
			jsonError = "";
			externalAppService
					.deleteExternalApp(new String[] { externalAppId });
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = e.getMessage();
		}
		return SUCCESS;
	}
	
	public List<ExternalApp> getAppList() {
		return appList;
	}

	@Override
	public ExternalApp getModel() {
		return externalApp;
	}

	public ExternalApp getExternalApp() {
		return externalApp;
	}

	public String getExternalAppId() {
		return externalAppId;
	}

	public void setExternalAppId(String externalAppId) {
		this.externalAppId = externalAppId;
	}

	public void setExternalApp(ExternalApp externalApp) {
		this.externalApp = externalApp;
	}

	public void setExternalAppService(ExternalAppService externalAppService) {
		this.externalAppService = externalAppService;
	}

}
