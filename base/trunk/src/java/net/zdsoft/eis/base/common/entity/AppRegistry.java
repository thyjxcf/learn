package net.zdsoft.eis.base.common.entity;

import java.util.List;

import net.zdsoft.eis.frame.client.BaseEntity;

public class AppRegistry extends BaseEntity {
    private static final long serialVersionUID = 3570092830268522694L;
    // Fields
    private String appcode;// 注册码
    private String appname;// 注册名称
    private String sysid;// 系统标识
    private String sysname;// 系统名称
    private String unitid;// 单位id
    private String isusing;// 是否在用
    private String image;// 显示图标
    private Integer displayorder;// 显示顺序
    private String description;// 描述
    private String url;// web地址
    private String xurl;// 相对于url的登录地址
    private String sharedataurl;// 数据同步url
    private String testurl;// 测试服务是否正常url
    private String type;// 接口类型
    private String parameters;// 参数
    private String checkuserurl;// 验证用户名和密码是否合法
    private String issync;// 是否同步
    private String databasepass;// 数据库密码
    private String isencoded;// 密码是否加密
    private String underlingUnitUse;// 是否供下属单位使用
    private Long appintid;// 注册整型id
    private String islogin;// 是否从统一桌面登录
    private String verifyKey;// 验证码
    private String parentId;
    private String source;
    private String sortType;

    // ====================辅助字段================
    private String sharedatasign;// 数据同步标识
    private String urlExample;// url样例
    private String appSystemType;// 应用系统类型：本地子系统和注册
    private List<AppRegistry> subAppList;
	/**
	 * 应用系统接口类型
	 */
	public static final String INTERFACE_TYPE_1 = "1"; // 同步用户信息
    
	//======================原fpf===================
    /**
     * 网站系统产品标志
     */
	public static final String SUBSYSTEM_WEBSITE_SIGN ="sbw"; 

    // Constructors

    /** default constructor */
    public AppRegistry() {
    }

    // Property accessors

    public String getSysid() {
        return this.sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
    }

    public String getAppname() {
        return this.appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getIsusing() {
        return this.isusing;
    }

    public void setIsusing(String isusing) {
        this.isusing = isusing;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getDisplayorder() {
        return this.displayorder;
    }

    public void setDisplayorder(Integer displayorder) {
        this.displayorder = displayorder;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getXurl() {
        return this.xurl;
    }

    public void setXurl(String xurl) {
        this.xurl = xurl;
    }

    public String getSharedataurl() {
        return this.sharedataurl;
    }

    public void setSharedataurl(String sharedataurl) {
        this.sharedataurl = sharedataurl;
    }

    public String getTesturl() {
        return this.testurl;
    }

    public void setTesturl(String testurl) {
        this.testurl = testurl;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParameters() {
        return this.parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getCheckuserurl() {
        return this.checkuserurl;
    }

    public void setCheckuserurl(String checkuserurl) {
        this.checkuserurl = checkuserurl;
    }

    public String getDatabasepass() {
        return this.databasepass;
    }

    public void setDatabasepass(String databasepass) {
        this.databasepass = databasepass;
    }

    public String getIsencoded() {
        return this.isencoded;
    }

    public void setIsencoded(String isencoded) {
        this.isencoded = isencoded;
    }

    public String getSysname() {
        return sysname;
    }

    public void setSysname(String sysname) {
        this.sysname = sysname;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getIssync() {
        return issync;
    }

    public void setIssync(String issync) {
        this.issync = issync;
    }

    public String getUnderlingUnitUse() {
        return underlingUnitUse;
    }

    public void setUnderlingUnitUse(String underlingUnitUse) {
        this.underlingUnitUse = underlingUnitUse;
    }

    public Long getAppintid() {
        return appintid;
    }

    public void setAppintid(Long appintid) {
        this.appintid = appintid;
    }

    public String getIslogin() {
        return islogin;
    }

    public void setIslogin(String islogin) {
        this.islogin = islogin;
    }

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }

    public String getSharedatasign() {
        return sharedatasign;
    }

    public void setSharedatasign(String sharedatasign) {
        this.sharedatasign = sharedatasign;
    }

    public String getUrlExample() {
        return urlExample;
    }

    public void setUrlExample(String urlExample) {
        this.urlExample = urlExample;
    }

    public String getAppSystemType() {
        return appSystemType;
    }

    public void setAppSystemType(String appSystemType) {
        this.appSystemType = appSystemType;
    }

	public List<AppRegistry> getSubAppList() {
		return subAppList;
	}

	public void setSubAppList(List<AppRegistry> subAppList) {
		this.subAppList = subAppList;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

}
