package net.zdsoft.eis.system.data.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.storage.StorageFileService;
import net.zdsoft.eis.system.data.dao.ExternalAppDao;
import net.zdsoft.eis.system.data.entity.ExternalApp;
import net.zdsoft.eis.system.data.service.ExternalAppService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.action.UploadFile;

public class ExternalAppServiceImpl implements ExternalAppService {

	private ExternalAppDao externalAppDao;

	private StorageFileService storageFileService;

	public void setExternalAppDao(ExternalAppDao externalAppDao) {
		this.externalAppDao = externalAppDao;
	}

	public void setStorageFileService(StorageFileService storageFileService) {
		this.storageFileService = storageFileService;
	}

	@Override
	public void addExternalApp(ExternalApp app, UploadFile file) {
		app.setObjectUnitId(app.getUnitId());
		if (file != null)
			storageFileService.saveFile(app, file);
		externalAppDao.addExternalApp(app);
	}

	@Override
	public void updateExternalApp(ExternalApp app) {
		externalAppDao.updateExternalApp(app);
	}

	@Override
	public void deleteExternalApp(String... ids) {
		externalAppDao.deleteExternalApp(ids);
	}

	public void updateExternalAppNotTemp(String... ids) {
		externalAppDao.updateExternalAppNotTemp(ids);
	}

	@Override
	public List<ExternalApp> getExternalAppListByUnitId(String unitId,
			int type, int num, boolean temp) {
		List<ExternalApp> appList = externalAppDao.getExternalAppListByUnitId(
				unitId, type, num, temp);
		// if (ExternalApp.EXTERNAL_APP == type) {
		for (ExternalApp app : appList) {
			if (StringUtils.isNotBlank(app.getFilePath()))
				storageFileService.setDirPath(app);
		}
		// }
		return appList;
	}

	@Override
	public List<ExternalApp> getExternalAppListByUnionId(String unionId,
			int type, int num, boolean temp) {
		return externalAppDao.getExternalAppListByUnionId(unionId, type, num,
				temp);
	}

	@Override
	public List<ExternalApp> getExternalAppListByCondition(String unionId,
			int type, String unitName, boolean temp, Pagination page) {
		return externalAppDao.getExternalAppListByCondition(unionId, type,
				unitName, temp, page);
	}
}
