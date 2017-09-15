package net.zdsoft.eis.base.common.service;

import java.util.List;



/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: McodeService.java,v 1.3 2006/09/15 10:13:24 dongzk Exp $
 */
public interface McodeService {
	
	/**
	 * 得到微代码类型的一个Mcode对象的实现，包括下面的所有明细
	 * @param mcodeid
	 * @return Mcode
	 */
	public Mcode getMcode(String mcodeId);
	
	/**
	 * 得到微代码类型的一个Mcode对象的实现，根据includeNoIsusing确定是否包提非在用明细
	 * @param mcodeid
	 * @param includeNoIsusing true包括非在用明细，false只显示在用明细
	 * @return Mcode
	 */
	public Mcode getMcode(String mcodeId, boolean includeNoIsusing);
	
	
	/**
     * 得到微代码类型的一个Mcode对象的实现，只是符合条件的明细
     * 
     * @param mcodeID
     * @param thisId like '1%'
     * @return Mcode
     */
    public Mcode getMcodeFaintness(String mcodeId, String thisId);
	
	
	/**
	 * 得到微代码明细项的String[]列表，string[0]=thisId，string[1]=context数组表列
	 * @param mcodeId 微代码类型
	 * @return List
	 */
	public List<String[]> getMcodeAarray(String mcodeId);	

	/**
	 * 根据微代码及明细代码得到名称
	 * @param mcodeId
	 * @param mDetailId
	 * @return String
	 */
	public String getMcodeContext(String mcodeId, String mDetailId);
	
	
}



