package net.zdsoft.eis.base.subsystemcall.service;


public interface SystemSubsystemService {
    /**
     * 可用单位数
     * 
     * @return
     */
    public int getUnitCountLimit();

    /**
     * 每单位用户数限制
     * 
     * @return
     */
    public int getUserCountLimit();
    
    
	/**
	 * 应用权限接口(手机端)
	 * @param remoteParam
	 * @return
	 */
	String officeAuth(String remoteParam);
	
}
