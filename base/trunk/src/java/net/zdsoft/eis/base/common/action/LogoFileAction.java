package net.zdsoft.eis.base.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.URLUtils;
import net.zdsoft.keelcnet.config.BootstrapManager;

import org.apache.struts2.ServletActionContext;

public class LogoFileAction extends BaseAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 7489861512040592589L;
	private static final String LOGO_FILE_PATH = "logo";

	private String filePath;

	public String execute() throws Exception {
		response = ServletActionContext.getResponse();

		try {
			String path = BootstrapManager.getStoreHome() + File.separator
						+ LOGO_FILE_PATH + File.separator + filePath;

			File storefile = new File(path);

			if (!storefile.exists()) {
				throw new FileNotFoundException(storefile.getPath());
			}

			if (log.isDebugEnabled()) {
				log.debug("读取文件: " + storefile.getAbsolutePath());
			}

			InputStream data = new FileInputStream(storefile);
			int fileSize = (int) storefile.length();
			String mimeType = this.getServletContext().getMimeType(
					storefile.getAbsolutePath());

			if ((mimeType == null) || mimeType.trim().equals("")) {
				mimeType = "application/unknown";
			} else if (mimeType.startsWith("text/html")) {
				mimeType = "application/unknown";
			}
			mimeType = "application/x-msdownload";

			response.setHeader("Content-Disposition", "attachment; filename="
					+ URLUtils.encode(storefile.getName(), "UTF-8"));

			response.setContentType(mimeType);
			response.setContentLength(fileSize);
			FileUtils.serveFile(response, data);
		} catch (Exception e) {
			promptMessageDto.setErrorMessage("文件下载出错！\n错误信息[" + e.getMessage()
					+ "]");
			return PROMPTMSG;
		}
		return NONE;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
