package net.zdsoft.eis.frame.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.helper.Nothing;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.keelcnet.config.ContainerManager;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

public class KindeditorUploadUtils {
	/**
	 * 编辑器中的图片等附件地址
	 */
	public static String STORE_PATH_TYPE_EDITOR = getStorePathEditor();
	/**
	 * 活动附件地址
	 */
	public static String STORE_PATH_TYPE_ATTACHMENT = getStorePathAttachment();
	/**
	 * 编辑器中的图片等附件URL地址
	 */
	public static String STORE_URL_EDITOR = getStoreURLEditor();

	private static String getStorePathEditor() {
		SimpleDateFormat curDate = new SimpleDateFormat("yyyyMMdd");
		String storePath = BootstrapManager.getStoreHome() + File.separator
				+ "activity" + File.separator + "editor" + File.separator
				+ curDate.format(new Date());
		return storePath;
	}

	private static String getStoreURLEditor() {

		Properties p = FileUtils.readProperties(new Nothing().getClass()
				.getClassLoader().getResourceAsStream("keelcnet.properties"));
		String s = p.getProperty("storeFolder");
		if (StringUtils.isBlank(s)) {
			s = "/store";
		}

		SimpleDateFormat curDate = new SimpleDateFormat("yyyyMMdd");
		SysOptionService sysOptionService = (SysOptionService) ContainerManager
				.getComponent("sysOptionService");
		SysOption sysOption = sysOptionService.getSysOption(SysOption.FILE_URL);

		String u = "";
		if (sysOption != null) {
			u = sysOption.getNowValue();
			if (StringUtils.isBlank(u)) {
				HttpServletRequest request = ServletActionContext.getRequest();
				u = request.getScheme() + "://" + request.getServerName() + ":"
						+ request.getServerPort() + "/file";
			}
		} else {
			HttpServletRequest request = ServletActionContext.getRequest();
			u = request.getScheme() + "://" + request.getServerName() + ":"
					+ request.getServerPort() + "/file";
		}
		String saveUrl = u + s + File.separator + "activity" + File.separator
				+ "editor" + File.separator + curDate.format(new Date())
				+ File.separator;
		return saveUrl;
	}

	private static String getStorePathAttachment() {
		SimpleDateFormat curDate = new SimpleDateFormat("yyyyMMdd");
		String storePath = BootstrapManager.getStoreHome() + File.separator
				+ "activity" + File.separator + "attachment" + File.separator
				+ curDate.format(new Date());
		return storePath;
	}
}
