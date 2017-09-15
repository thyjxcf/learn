package net.zdsoft.eis.base.data.sync.schsecurity.entity;


/**
 * 统一身份用户表
 * @author Administrator
 *
 */
public class TYSFYHB extends SyncEntity{
	
	/**
	 * 用户ID
	 */
	public String YHID;
	
	/**
	 * 用户属性
	 */
	public String YHSX;
	
	/**
	 * 用户名
	 */
	public String YHM;
	
	/**
	 * 姓名
	 */
	public String XM;
	
	/**
	 * 单位号
	 */
	public String DWH;
	
	/**
	 * 证件类型
	 */
	public String ZJLX;
	
	/**
	 * 证件号码
	 */
	public String ZJHM;
	
	/**
	 * 手机号码
	 */
	public String SJHM;
	
	/**
	 * 账号状态
	 */
	public String STATUS;
	
	/**
	 * 性别
	 */
	public String XB;
	
	/**
	 * 更新时间
	 */
	public String GXSJ;
	
	/**
	 * 逻辑删除标识
	 */
	public String LJSCBZ;
	
	public String getYHID() {
		return YHID;
	}

	public void setYHID(String yHID) {
		YHID = yHID;
	}

	public String getYHM() {
		return YHM;
	}

	public void setYHM(String yHM) {
		YHM = yHM;
	}

	public String getXM() {
		return XM;
	}

	public void setXM(String xM) {
		XM = xM;
	}

	public String getDWH() {
		return DWH;
	}

	public void setDWH(String dWH) {
		DWH = dWH;
	}

	public String getZJLX() {
		return ZJLX;
	}

	public void setZJLX(String zJLX) {
		ZJLX = zJLX;
	}

	public String getZJHM() {
		return ZJHM;
	}

	public void setZJHM(String zJHM) {
		ZJHM = zJHM;
	}

	public String getSJHM() {
		return SJHM;
	}

	public void setSJHM(String sJHM) {
		SJHM = sJHM;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getXB() {
		return XB;
	}

	public void setXB(String xB) {
		XB = xB;
	}

	public String getGXSJ() {
		return GXSJ;
	}

	public void setGXSJ(String gXSJ) {
		GXSJ = gXSJ;
	}

	public String getLJSCBZ() {
		return LJSCBZ;
	}

	public void setLJSCBZ(String lJSCBZ) {
		LJSCBZ = lJSCBZ;
	}

	public String getYHSX() {
		return YHSX;
	}

	public void setYHSX(String yHSX) {
		YHSX = yHSX;
	}
}
