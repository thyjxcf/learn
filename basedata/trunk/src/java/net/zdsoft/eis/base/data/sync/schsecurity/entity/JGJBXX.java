package net.zdsoft.eis.base.data.sync.schsecurity.entity;

public class JGJBXX extends SyncEntity{
	
	/**
	 * 机构ID
	 */
	public String JGID;
	
	/**
	 * 机构标识码
	 */
	public String JGBSM;
	
	/**
	 * 机构名称
	 */
	public String JGMC;
	
	/**
	 * 隶属或归口管理机构代码
	 */
	public String LSJGM;
	
	/**
	 * 机构类别码
	 */
	public String JGLBM;
	
	/**
	 * 机构有效标识
	 */
	public String JGYXBS;
	
	/**
	 * 显示顺序
	 */
	public int XSSH;
	
	/**
	 * 地区编号
	 */
	public String DQBH;
	
	/**
	 * 更新时间
	 */
	public String GXSJ;
	
	/**
	 * 逻辑删除标识
	 */
	public String LJSCBZ;

	public String getJGID() {
		return JGID;
	}

	public void setJGID(String jGID) {
		JGID = jGID;
	}

	public String getJGMC() {
		return JGMC;
	}

	public void setJGMC(String jGMC) {
		JGMC = jGMC;
	}

	public String getLSJGM() {
		return LSJGM;
	}

	public void setLSJGM(String lSJGM) {
		LSJGM = lSJGM;
	}

	public String getJGYXBS() {
		return JGYXBS;
	}

	public void setJGYXBS(String jGYXBS) {
		JGYXBS = jGYXBS;
	}

	public int getXSSH() {
		return XSSH;
	}

	public void setXSSH(int xSSH) {
		XSSH = xSSH;
	}

	public String getDQBH() {
		return DQBH;
	}

	public void setDQBH(String dQBH) {
		DQBH = dQBH;
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

	public String getJGLBM() {
		return JGLBM;
	}

	public void setJGLBM(String jGLBM) {
		JGLBM = jGLBM;
	}

	public String getJGBSM() {
		return JGBSM;
	}

	public void setJGBSM(String jGBSM) {
		JGBSM = jGBSM;
	}
}
