package net.zdsoft.eis.system.desktop.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.entity.StorageDir.StorageDirType;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.entity.UserSet;
import net.zdsoft.eis.base.common.service.UserSetService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.util.SwfUploadUtils;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.keel.util.ImageUtils;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keel.util.UUIDUtils;
import net.zdsoft.keelcnet.config.BootstrapManager;
import net.zdsoft.keelcnet.util.FileUtils;

public class ImageModifyAction extends ImageUploadAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserSetService userSetService;

	private String filePath;

	private boolean displaySource = false;

	private boolean uploadNew = false;

	private String userSetId;

	// public static final String PIC_PATH = "user_image";
	// public static final String PIC_SOURCE = "source";

	public String execute() {
		return SUCCESS;
	}

	public String main() {
		User user = getLoginInfo().getUser();
		super.execute();
		String tempParentPath = BootstrapManager.getStoreHome()
				+ File.separator + SwfUploadUtils.SWF_UPLOAD_PATH
				+ File.separator
				+ DateUtils.date2String(new Date(), "yyyyMMdd")
				+ File.separator + albumId;
		if (!uploadNew) {
			UserSet userSet = userSetService.getUserSetByUserId(user.getId());
			// userSet.setDirPath(storageDirService.getDir(userSet.getDirId()));
			if (userSet != null) {
				this.fileId = userSet.getId();
				File tempDir = new File(tempParentPath);
				if (!tempDir.exists()) {
					tempDir.mkdirs();
				}
				try {
					// 将学生头像的源图片拷贝到临时目录
					// Account ac = accountService
					// .getAccountByAccountId(getLoginAccount().getAccountId());
					if (StringUtils.isNotBlank(userSet.getFilePath())) {
						String dir = storageDirService.getDir(userSet
								.getDirId());
						if (StringUtils.isNotBlank(dir)) {
							String picPath = dir + File.separator
									+ userSet.getFilePath();
							File pic = new File(picPath);// 小头像
							// String picPathSource = dir + File.separator
							// + userSet.getDefaultFilePath();
							// File picSource = new File(picPathSource);// 原头像
							if (pic.exists()) {
								File temp = new File(tempParentPath
										+ File.separator + fileId + ".jpg");
								// 学生头像原图
								File sourceFile = new File(pic.getParent()
										+ File.separator + UserSet.PIC_SOURCE
										+ ".jpg");
								if (sourceFile.exists()) {
									FileUtils.copyFile(sourceFile, temp);
								} else {
									FileUtils.copyFile(pic, temp);
								}
								filePath = temp.getAbsolutePath();
							}
							userSetId = userSet.getId();
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		} else {
			this.filePath = tempParentPath + File.separator + fileId + ".jpg";
		}
		return SUCCESS;
	}

	protected void dealWithPhoto(File uploadedFile, String fileName)
			throws IOException {
		String picName = this.fileId + ".jpg";
		// 文件保存路径 store/yyyyMMdd/albumId/fileId.jpg
		String dest = uploadedFile.getParentFile().getParent();
		File destPic = new File(dest + File.separator + picName);
		FileUtils.copyFile(uploadedFile, destPic);
		SwfUploadUtils.deleteFile(uploadedFile);
	}

	// protected String getDisplayImgFilePath() {
	// albumId = getLoginAccount().getUnitId();
	// return super.getDisplayImgFilePath();
	// }

	public String save() {
		User user = getLoginInfo().getUser();
		boolean isAdd = false;
		try {
			UserSet userSet = userSetService.getUserSetByUserId(user.getId());
			if (userSet != null) {
				fileId = userSet.getId();
			} else {
				userSet = new UserSet();
				fileId = UUIDUtils.newId();
				userSet.setId(fileId);
				isAdd = true;
			}
			// fileId = getLoginAccount().getAccountId();
			userSetId = fileId;
			// albumId = getLoginAccount().getUnitId();
			albumId = getUnitId();

			filePath = super.getDisplayImgFilePath();
			File tempSourceFile = new File(filePath);
			// 原来保存的头像
			// Account ac = accountService.getAccountByAccountId(accountId);
			StorageDir ndir = storageDirService.getActiveStorageDir();
			File of = null;
			// String path = ac.getFilePath();
			String path = userSet.getFilePath();
			boolean hasPic = false;
			// if (StringUtils.isNotBlank(ac.getFilePath())) {
			if (StringUtils.isNotBlank(userSet.getFilePath())) {
				// String dir = storageDirService.getDir(ac.getDirId());
				String dir = storageDirService.getDir(userSet.getDirId());
				if (dir != null) {
					// of = new File(dir + File.separator + ac.getFilePath());
					of = new File(dir + File.separator + userSet.getFilePath());
				}
			}
			String dest = "";
			// 编辑头像保存到的临时文件
			// File tempFile = new File(ndir.getDir() + File.separator +
			// Account.STUPIC_PATH + File.separator + Math.random()*10000 +
			// File.separator + "temp.jpg");;
			File tempFile = new File(ndir.getDir() + File.separator
					+ UserSet.PIC_PATH + File.separator + Math.random() * 10000
					+ File.separator + "temp.jpg");
			;
			File sourceFile = null;
			if (of == null || !of.exists() || !of.isFile()) {
				path = StorageDirType.PICTURE.getSubdirectory()
						+ File.separator + UserSet.PIC_PATH + File.separator
						+ getUnitId() + File.separator + getCurrentYear()
						+ File.separator + userSetId;
				File direc = new File(ndir.getDir() + File.separator + path);
				if (!direc.exists()) {
					direc.mkdirs();
				}
				sourceFile = new File(direc.getAbsolutePath() + File.separator
						+ UserSet.PIC_SOURCE + ".jpg");
				path += (File.separator + userSetId + ".jpg");
				dest = direc.getAbsolutePath() + File.separator + userSetId
						+ ".jpg";
			} else {
				hasPic = true;
				dest = of.getAbsolutePath();
				sourceFile = new File(of.getParent() + File.separator
						+ UserSet.PIC_SOURCE + ".jpg");
			}

			FileUtils.copyFile(getRequest().getInputStream(), tempFile);
			if (tempSourceFile.exists()) {
				FileUtils.copyFile(tempSourceFile, sourceFile);
			}
			int wid = 76;
			int height = 76;
			// 等比例修改图片大小
			ImageUtils.changeOppositeSize(tempFile.getAbsolutePath(), dest,
					wid, height);
			if (!hasPic) {
				// ac.setDirId(ndir.getId());
				// ac.setFilePath(path);
				// accountService.updateAccount(ac);
				userSet.setDirId(ndir.getId());
				userSet.setFilePath(path);
				// userSet.setDefaultDirId(defaultDirId);
				// userSet.setDefaultFilePath(defaultFilePath);
				if (isAdd) {
					userSet.setUserId(user.getId());
					userSetService.save(userSet);
				} else {
					userSetService.updateUserSetFile(userSet);
				}
			}

			// 删除临时文件
			SwfUploadUtils.deleteFile(tempFile.getParentFile());
			SwfUploadUtils.deleteFile(tempSourceFile);
			// 返回
			ServletUtils.print(getResponse(), BaseConstant.STR_YES);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return NONE;
	}

	public String getRandom() {
		return Math.random() * 1000 + "";
	}

	public boolean isUploadNew() {
		return uploadNew;
	}

	public void setUploadNew(boolean uploadNew) {
		this.uploadNew = uploadNew;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setUserSetService(UserSetService userSetService) {
		this.userSetService = userSetService;
	}

	public String getUserSetId() {
		return userSetId;
	}

	public void setUserSetId(String userSetId) {
		this.userSetId = userSetId;
	}

	public boolean isDisplaySource() {
		return displaySource;
	}

	public void setDisplaySource(boolean displaySource) {
		this.displaySource = displaySource;
	}

}
