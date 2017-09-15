package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.base.photo.PhotoEntity;

public class UserSet extends PhotoEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4667680739508606025L;

	private String userId;
	
	private String deskApp;

	public String skin;
	
	private String appClosed;
	
	/**
	 * 布局
	 */
	private String layout;
	/**
	 * 主题
	 */
	private String theme;
	/**
	 * 背景图片
	 */
	private String backgroundImg;
	/**
	 * 背景色
	 */
	private String backgroundColor;
	
//	private String defaultDirId;//桌面头像原图片
//	
//	private String defaultFilePath;
	
	private String filePath;//桌面头像展示图片
	
	private String dirId;
	
	public static final String PIC_PATH = "user_image";
	public static final String PIC_SOURCE = "source";
	public static final String LAYOUT_UP = "1"; // 布局：上下结构
	public static final String LAYOUT_LEFT = "2"; // 布局：左右结构
	public static final String SKIN_DEFAULT = "default";// 默认皮肤
	public static final String SKIN_DEFAULT_STUDENT = "yellow";// 默认皮肤-学生
	public static final String SKIN_DEFAULT_FAMILY = "green";// 默认皮肤-家长
	public static final String THEME_DEFAULT = "2";// 默认主题，个人中心
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getAppClosed() {
		return appClosed;
	}

	public void setAppClosed(String appClosed) {
		this.appClosed = appClosed;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDirId() {
		return dirId;
	}

	public void setDirId(String showDirId) {
		this.dirId = showDirId;
	}

	public String getObjectType() {
		return PIC_PATH;
	}

	public String getDeskApp() {
		return deskApp;
	}

	public void setDeskApp(String deskApp) {
		this.deskApp = deskApp;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getBackgroundImg() {
		return backgroundImg;
	}

	public void setBackgroundImg(String backgroundImg) {
		this.backgroundImg = backgroundImg;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
