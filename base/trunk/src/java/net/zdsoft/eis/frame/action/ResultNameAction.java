package net.zdsoft.eis.frame.action;


/* 
 * 添加学籍系统中Action特有的返回名称，一般是公用的返回名称
 * 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: ResultNameAction.java,v 1.2 2007/01/17 08:22:30 luxm Exp $
 */
public interface ResultNameAction {
	
	/**
	 * 返回抛出异常显示页面（不用编码实现，自动跳转）
	 */
	public static final String ROOTEXCEPTION = "rootException";
	
	
	/**
	 * 返回提示信息显示页面（需要编码实现，设置要显示的信息及下一步的操作）
	 */
	public static final String PROMPTMSG = "promptMsg";
	
	
	/**
	 * 返回无权限操作提示页面
	 */
	public static final String NOPERMISSION = "nopermission";
	
    /**
     * 返回明细编辑页面
     */
    public static final String EDIT = "edit";
    
    /**
     * 返回列表页面
     */
    public static final String LIST = "list";
	
	
}



