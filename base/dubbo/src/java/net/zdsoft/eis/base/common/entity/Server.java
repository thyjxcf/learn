package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;

import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;

public class Server extends BaseEntity {
	
	
	public static Server dc(String data) {
		return SUtils.dc(data, Server.class);
	}
	
	public static List<Server> dt(String data) {
		List<Server> ts = SUtils.dt(data, new TypeReference<List<Server>>() {
		});
		if (ts == null)
			ts = new ArrayList<Server>();
		return ts;

	}
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -4529602957887046470L;

	public static final String SERVERCODE_OFFICE = "office";
	public static final String SERVERCODE_ARCHIVE = "archive";
	public static final String SERVERCODE_ESS = "ess";
	public static final String SERVERCODE_STUSYS = "stusys";
	public static final String SERVERCODE_OFFICEDOC = "officedoc";

	/**
	 * 状态：启用
	 */
	public static final int STATUS_TURNON = 1;

	/**
	 * 内部server
	 */
	public static final int SERVER_INNER = 1;

	/**
	 * 外部server
	 */
	public static final int SERVER_OUTER = 2;

	private String name;
	private int status;
	private String code;
	@JSONField(name="baseSyncType")
	private int seSyncType;
	private int interfaceType;
	private String capabilityurl;
	private String introduceurl;
	private String indexUrl;
	private String linkPhone;
	private String linkMan;
	private String appoint;
	private String serverKey;
	private String protocol;
	private String domain;
	private String secondDomain;
	private int port;
	private String serverCode;

	private long serverTypeId;
	@JSONField(name="isPassport")
	//TODO v7里面的字段是Integer类型 ？？？
	private boolean passport;
	private String context;

	// ===================輔助字段==========================
	private boolean checked;// 是否选中

	public String getUrl() {
		if (StringUtils.isEmpty(context)) {
			return protocol + "://" + domain + ":" + port;
		} else {
			if (context.substring(0, 1).equals("/")) {
				return protocol + "://" + domain + ":" + port + context;
			} else {
				return protocol + "://" + domain + ":" + port + "/" + context;
			}
		}

	}

	public String getSecondUrl() {
		if (StringUtils.isEmpty(context)) {
			return protocol + "://" + secondDomain + ":" + port;
		} else {
			if (context.substring(0, 1).equals("/")) {
				return protocol + "://" + secondDomain + ":" + port + context;
			} else {
				return protocol + "://" + secondDomain + ":" + port + "/" + context;
			}
		}

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSeSyncType() {
		return seSyncType;
	}

	public void setSeSyncType(int seSyncType) {
		this.seSyncType = seSyncType;
	}

	public int getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(int interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getCapabilityurl() {
		return capabilityurl;
	}

	public void setCapabilityurl(String capabilityurl) {
		this.capabilityurl = capabilityurl;
	}

	public String getIntroduceurl() {
		return introduceurl;
	}

	public void setIntroduceurl(String introduceurl) {
		this.introduceurl = introduceurl;
	}

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getAppoint() {
		return appoint;
	}

	public void setAppoint(String appoint) {
		this.appoint = appoint;
	}

	public String getServerKey() {
		return serverKey;
	}

	public void setServerKey(String serverKey) {
		this.serverKey = serverKey;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocal) {
		this.protocol = protocal;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getServerCode() {
		return serverCode;
	}

	public void setServerCode(String serverCode) {
		this.serverCode = serverCode;
	}

	public long getServerTypeId() {
		return serverTypeId;
	}

	public void setServerTypeId(long serverTypeId) {
		this.serverTypeId = serverTypeId;
	}

	public boolean isPassport() {
		return passport;
	}

	public void setPassport(boolean passport) {
		this.passport = passport;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getSecondDomain() {
		return secondDomain;
	}

	public void setSecondDomain(String secondDomain) {
		this.secondDomain = secondDomain;
	}

}
